package com.example.cmsapi.services;

import com.example.cmsapi.model.User;
import com.example.cmsapi.dto.UserLoginDTO;
import com.example.cmsapi.repositories.UserRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @ApiResponse(responseCode = "401", description = "Invalid credentials",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(name = "Unauthorized example",
                            value = "{ \"error\": Invalid username or password\".\" }")
            ))
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
