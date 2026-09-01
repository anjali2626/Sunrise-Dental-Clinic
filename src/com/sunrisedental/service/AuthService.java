package com.sunrisedental.service;

import com.sunrisedental.model.User;

import java.sql.SQLException;

public class AuthService {

    private final UserService userService;

    public AuthService() {
        this.userService = new UserService();
    }

    public User login(
            String username,
            String password)
            throws SQLException {

        if (username == null ||
                username.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username is required.");
        }

        if (password == null ||
                password.isEmpty()) {

            throw new IllegalArgumentException(
                    "Password is required.");
        }

        User user =
                userService.findUserByUsername(
                        username.trim());

        if (user == null) {
            return null;
        }

        boolean validPassword =
                com.sunrisedental.util.PasswordUtil
                        .validatePassword(
                                password,
                                user.getPasswordHash());

        if (!validPassword) {
            return null;
        }

        return user;
    }
}