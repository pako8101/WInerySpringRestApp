package com.kamenov.wineryspringrestapp.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

    @Size(min = 3,max = 20,message = "Username must be between 3 and 20 symbols!")
    @NotNull(message = "Username must not be empty!")
    private String username;
    @Size(min = 3,max = 20,message = "Password must be between 3 and 20 symbols!")
    @NotNull(message = "Password must not be empty!")
    private String password;

    public UserLoginDto() {}

    public @Size(min = 3, max = 20, message = "Username must be between 3 and 20 symbols!") @NotNull(message = "Username must not be empty!") String getUsername() {
        return username;
    }

    public UserLoginDto setUsername(@Size(min = 3, max = 20, message = "Username must be between 3 and 20 symbols!") @NotNull(message = "Username must not be empty!") String username) {
        this.username = username;
        return this;
    }

    public @Size(min = 3, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String getPassword() {
        return password;
    }

    public UserLoginDto setPassword(@Size(min = 3, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
