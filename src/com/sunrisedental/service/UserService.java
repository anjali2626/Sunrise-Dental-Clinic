package com.sunrisedental.service;

import com.sunrisedental.model.User;
import com.sunrisedental.repository.UserRepository;

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
}