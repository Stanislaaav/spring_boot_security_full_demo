package com.example.demo.config;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "application.jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private String tokenExpirationAfterDays;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
