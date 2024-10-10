package com.alvise1.taskManagementApi.service;

import com.alvise1.taskManagementApi.model.AppUser;
import com.alvise1.taskManagementApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        if (password.length() < 10) {
            throw new IllegalArgumentException("Password must be at least 10 characters long!");
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one number.");
        }
        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one special character.");
        }
    }

    public AppUser registerUser(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        validatePassword(password);

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(appUser);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
