package com.example.cmsapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection
                .csrf(csrf -> csrf.disable())

                // Configure request authorization
                .authorizeHttpRequests(auth -> {auth

                        // Allow all POST requests to /api/auth/**
                        .requestMatchers(HttpMethod.POST, "/api/auth/**")
                        .permitAll()

                        // Allow all GET requests to /api/**
                        .requestMatchers(HttpMethod.GET, "api/**")
                        .permitAll()

                        // Require ADMIN role for all other requests
                        .anyRequest()
                        .hasRole("ADMIN");
                })
                // Configure session management to be stateless
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Set the custom authentication provider
                .authenticationProvider(authenticationProvider)

                // Add custom JWT authentication filter before the default UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the SecurityFilterChain
        return http.build();
    }


}
