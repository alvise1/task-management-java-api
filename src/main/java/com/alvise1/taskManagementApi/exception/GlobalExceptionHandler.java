package com.alvise1.taskManagementApi.exception;

import com.alvise1.taskManagementApi.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Illegal argument exception: {}", ex.getMessage());
        ApiResponse<String> response = new ApiResponse<>(null, ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidTokenException(InvalidTokenException ex) {
        logger.error("Invalid token exception: {}", ex.getMessage());
        ApiResponse<String> response = new ApiResponse<>(null, ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(BindException ex) {
        StringBuilder messageBuilder = new StringBuilder("Validation failed for fields: ");
        for (FieldError error : ex.getFieldErrors()) {
            messageBuilder.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }
        String message = messageBuilder.toString();
        logger.error("Validation exception: {}", message);
        ApiResponse<String> response = new ApiResponse<>(null, message, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Generic exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage());
        ApiResponse<String> response = new ApiResponse<>(null, "An unexpected error occurred. Please try again later.", false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
