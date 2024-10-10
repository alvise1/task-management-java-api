package com.alvise1.taskManagementApi.service;

import com.alvise1.taskManagementApi.model.AppUser;
import com.alvise1.taskManagementApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser registerUser(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(appUser);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
