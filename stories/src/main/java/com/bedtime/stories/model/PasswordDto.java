package com.bedtime.stories.model;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;

public class PasswordDto {
    @SafeHtml
    @Size(min=8, message="Invalid credentials!")
    private String oldPassword;
    @SafeHtml
    @Size(min=8, message="Password should have at least 8 characters")
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
