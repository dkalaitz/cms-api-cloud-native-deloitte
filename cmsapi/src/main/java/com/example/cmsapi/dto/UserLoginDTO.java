package com.example.cmsapi.dto;

import jakarta.validation.constraints.NotEmpty;

public class UserLoginDTO {

    @NotEmpty(message = "Username should not be null or blank")
    private String username;
    @NotEmpty(message = "Password should not be null or blank")
    private String password;

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
}
