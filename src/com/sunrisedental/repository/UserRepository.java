package com.sunrisedental.repository;

import com.sunrisedental.model.User;
import com.sunrisedental.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public User findByUsername(String username)
            throws SQLException {

        String sql = """
                SELECT user_id, username, password_hash,
                       full_name, role, created_at
                FROM users
                WHERE username = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        }

        return null;
    }

    private User mapResultSetToUser(ResultSet resultSet)
            throws SQLException {

        User user = new User();

        user.setUserId(
                resultSet.getInt("user_id"));

        user.setUsername(
                resultSet.getString("username"));

        user.setPasswordHash(
                resultSet.getString("password_hash"));

        user.setFullName(
                resultSet.getString("full_name"));

        user.setRole(
                resultSet.getString("role"));

        if (resultSet.getTimestamp("created_at") != null) {
            user.setCreatedAt(
                    resultSet.getTimestamp("created_at")
                            .toLocalDateTime());
        }

        return user;
    }
}