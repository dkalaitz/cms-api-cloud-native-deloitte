package com.example.cmsapi.service;

import com.example.cmsapi.enumeration.Role;
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

    public void registerUser(String username, String password, Role role) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, role);
        userRepository.save(user);
    }

}
