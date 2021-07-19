package com.example.demo.config;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtTokenVerifier(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
        }

        String token = authorizationHeader.replace("Bearer ", "");

        try {
            String secretKey = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";

            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            List<GrantedAuthority> authorities = (List<GrantedAuthority>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.getAuthority()))
                .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be truest", token));
        }

        filterChain.doFilter(request, response);
    }
}
