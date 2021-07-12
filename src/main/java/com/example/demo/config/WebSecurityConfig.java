package com.example.demo.config;

import static com.example.demo.enums.UserPermissionEnum.COURSE_WRITE;
import static com.example.demo.enums.UserRoleEnum.ADMIN;
import static com.example.demo.enums.UserRoleEnum.ADMINTRAINEE;
import static com.example.demo.enums.UserRoleEnum.STUDENT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/api/v1/**").hasRole(STUDENT.name())
//            .antMatchers(DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // that go to controller ass Preathorized
//            .antMatchers(POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // that go to controller ass Preathorized
//            .antMatchers(PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // that go to controller ass Preathorized
//            .antMatchers( "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name()) // that go to controller ass Preathorized
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {

        UserDetails ana =  User.builder()
            .username("ana")
            .password(passwordEncoder.encode("pass"))
//            .roles(STUDENT.name())
            .authorities(STUDENT.getGrantedAutorities())
            .build();

        UserDetails linda =  User.builder()
            .username("linda")
            .password(passwordEncoder.encode("pass1"))
//            .roles(ADMIN.name())
            .authorities(ADMIN.getGrantedAutorities())
            .build();

        UserDetails tom =  User.builder()
            .username("tom")
            .password(passwordEncoder.encode("pass1"))
//            .roles(ADMINTRAINEE.name())
            .authorities(ADMINTRAINEE.getGrantedAutorities())
            .build();

      return new InMemoryUserDetailsManager(ana, linda, tom);

    }
}
