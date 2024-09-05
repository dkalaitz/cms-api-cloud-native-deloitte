package com.example.cmsapi.config;

import com.example.cmsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Autowired
    private UserRepository userRepository;

    /**
     * Bean for UserDetailsService.
     * This service is used by Spring Security to load user-specific data.
     * It fetches user details from the UserRepository based on the username.
     * @return UserDetailsService implementation that finds a user by username.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Provides a BCryptPasswordEncoder for hashing passwords.
     * @return BCryptPasswordEncoder for secure password encoding.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for AuthenticationManager.
     * AuthenticationManager is responsible for handling authentication requests.
     * It is configured using the AuthenticationConfiguration object.
     * @param config AuthenticationConfiguration used to build the AuthenticationManager.
     * @return AuthenticationManager instance.
     * @throws Exception if there is an issue retrieving the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean for AuthenticationProvider.
     * DaoAuthenticationProvider is configured with UserDetailsService and PasswordEncoder.
     * It performs the authentication using the provided user details service and password encoder.
     * @return DaoAuthenticationProvider configured with userDetailsService and passwordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Set the UserDetailsService to use for loading user data
        authProvider.setUserDetailsService(userDetailsService());

        // Set the PasswordEncoder to use for encoding and matching passwords
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}