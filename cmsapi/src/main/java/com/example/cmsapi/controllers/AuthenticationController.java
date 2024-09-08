package com.example.cmsapi.controllers;

import com.example.cmsapi.services.AuthenticationService;
import com.example.cmsapi.services.JwtService;
import com.example.cmsapi.model.User;
import com.example.cmsapi.dto.UserLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication", description = "Operations related to user authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    // Authenticate the user and generate a JWT token
    @Operation(
            summary = "Login User",
            description = """
        Authenticate a user and return a JWT Token.

        **Responses**:

        - `200 OK`: Returns the JWT token in the response body.
        - `401 Unauthorized`: Invalid username or password.
        """
    )
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody UserLoginDTO userLoginDTO) {

        // Authenticate the user using the provided login details
        User authenticatedUser = authenticationService.authenticate(userLoginDTO);

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Return the JWT token with HTTP 200 OK status
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
