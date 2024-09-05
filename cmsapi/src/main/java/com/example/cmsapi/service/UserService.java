package com.example.cmsapi.service;

import com.example.cmsapi.enumeration.Role;
import com.example.cmsapi.exception.UserExceptions;
import com.example.cmsapi.model.User;
import com.example.cmsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // Register a new user
    public void registerUser(String username, String password, Role role) {

        // Check if a user with the same username exists
        if (userRepository.existsByUsername(username)){
            throw new UserExceptions.UsernameExistsException("Username already exists.");
        }

        // Encode password for security
        String encodedPassword = passwordEncoder.encode(password);

        // Create a new user entity and save it to the repository
        User user = new User(username, encodedPassword, role);
        userRepository.save(user);
    }

}
