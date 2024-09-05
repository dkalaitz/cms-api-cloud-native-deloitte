package com.example.cmsapi.service;

import com.example.cmsapi.model.User;
import com.example.cmsapi.dto.UserLoginDTO;
import com.example.cmsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Authenticate User based on username and password
    public User authenticate(UserLoginDTO loginDetails) {

        // Attempt to authenticate the user using the authentication manager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetails.getUsername(),
                        loginDetails.getPassword()
                )
        );

        // Retrieve and return the user associated with the provided username
        return userRepository.findByUsername(loginDetails.getUsername())
                .orElseThrow();
    }
}
