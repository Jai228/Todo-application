package com.example.Todo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable CSRF protection for API testing
            .cors(cors -> {})             // enable CORS support
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // allow all requests without authentication
            )
            .formLogin(form -> form.disable()) // disable form login
            .httpBasic(basic -> basic.disable()); // disable HTTP Basic authentication

        return http.build();
    }

    
}

