package com.alvise1.taskManagementApi.controller;

import com.alvise1.taskManagementApi.model.ApiResponse;
import com.alvise1.taskManagementApi.security.JwtAuthenticationFilter;
import com.alvise1.taskManagementApi.security.JwtTokenProvider;
import com.alvise1.taskManagementApi.model.AppUser;
import com.alvise1.taskManagementApi.service.TokenBlacklistService;
import com.alvise1.taskManagementApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import com.alvise1.taskManagementApi.model.ChangePasswordRequest;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AppUser>> registerUser(@Valid @RequestBody AppUser appUser) {
        try {
            AppUser createdAppUser = userService.registerUser(appUser.getUsername(), appUser.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(createdAppUser, "User registered successfully.", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(null, e.getMessage(), false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody AppUser appUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication.getName());
            return ResponseEntity.ok(new ApiResponse<>(jwt, "Login successful.", true));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(null, "Invalid username or password.", false));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        String jwt = jwtAuthenticationFilter.getJwtFromRequest(request);
        if (jwt != null) {
            tokenBlacklistService.addTokenToBlacklist(jwt, LocalDateTime.now());
            return ResponseEntity.ok(new ApiResponse<>(null,"Logout successful!", true));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, "Token not found", false));
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            userService.changePassword(username, changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword());
            return ResponseEntity.ok(new ApiResponse<>(null, "Password changed successfully!", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, e.getMessage(), false));
        }
    }
}
