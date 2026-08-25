package com.sunrisedental.service;

import com.sunrisedental.model.User;
import com.sunrisedental.repository.UserRepository;
import com.sunrisedental.util.PasswordUtil;

import java.sql.SQLException;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public User findUserByUsername(String username)
            throws SQLException {

        if (username == null ||
                username.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username is required.");
        }

        return userRepository.findByUsername(
                username.trim());
    }

    public User authenticate(
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
                userRepository.findByUsername(
                        username.trim());

        if (user == null) {
            return null;
        }

        boolean passwordValid =
                PasswordUtil.validatePassword(
                        password,
                        user.getPasswordHash());

        if (!passwordValid) {
            return null;
        }

        return user;
    }
}