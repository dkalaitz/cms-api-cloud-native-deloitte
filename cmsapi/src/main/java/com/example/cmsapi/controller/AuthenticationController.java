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

    @RequestMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody UserLoginDTO userLoginDTO){
        User authenticatedUser = authenticationService.authenticate(userLoginDTO);
        return new ResponseEntity<>(jwtService.generateToken(authenticatedUser), HttpStatus.OK);
    }
}
