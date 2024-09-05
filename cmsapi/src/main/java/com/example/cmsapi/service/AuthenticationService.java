package com.example.cmsapi.service;

import com.example.cmsapi.model.User;
import com.example.cmsapi.dto.UserLoginDTO;
import com.example.cmsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User authenticate(UserLoginDTO loginDetails) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetails.getUsername(),
                        loginDetails.getPassword()
                )
        );
        return userRepository.findByUsername(loginDetails.getUsername())
                .orElseThrow();
    }
}
