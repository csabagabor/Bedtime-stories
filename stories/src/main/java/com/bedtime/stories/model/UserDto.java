package com.bedtime.stories.model;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotEmpty;

public class UserDto {

    @NotEmpty
    @SafeHtml
    private String username;

    @NotEmpty
    @SafeHtml
    private String email;

    @NotEmpty
    @SafeHtml
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
