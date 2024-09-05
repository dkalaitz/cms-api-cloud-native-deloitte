package com.example.cmsapi.controller;

import com.example.cmsapi.service.AuthenticationService;
import com.example.cmsapi.service.JwtService;
import com.example.cmsapi.model.User;
import com.example.cmsapi.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    // Authenticate the user and generate a JWT token
    @RequestMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody UserLoginDTO userLoginDTO) {

        // Authenticate the user using the provided login details
        User authenticatedUser = authenticationService.authenticate(userLoginDTO);

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Return the JWT token with HTTP 200 OK status
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
