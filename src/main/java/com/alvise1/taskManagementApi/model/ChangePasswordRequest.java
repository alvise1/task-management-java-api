package com.alvise1.taskManagementApi.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {

    @NotEmpty(message = "Current password cannot be empty")
    private String currentPassword;

    @NotEmpty(message = "New password cannot be empty")
    @Size(min = 10, message = "New password must be at least 10 characters long")
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
