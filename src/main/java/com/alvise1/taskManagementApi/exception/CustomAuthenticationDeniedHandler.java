package com.alvise1.taskManagementApi.exception;

import com.alvise1.taskManagementApi.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationDeniedHandler implements AuthenticationEntryPoint{

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationDeniedHandler.class);
    private final ObjectMapper objectMapper;

    public CustomAuthenticationDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized request - {}", authException.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(null, "Authentication is required to access this resource.", false);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
