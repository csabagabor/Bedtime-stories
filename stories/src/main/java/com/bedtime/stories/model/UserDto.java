package com.bedtime.stories.model;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    @SafeHtml
    @Size(min=5, message="Username should have at least 5 characters")
    private String username;

    @SafeHtml
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email;

    @SafeHtml
    @Size(min=8, message="Password should have at least 8 characters")
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
