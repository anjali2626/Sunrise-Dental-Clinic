package com.sunrisedental.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection != null) {
                System.out.println("Database connection successful!");
                System.out.println("Connected to: " 
                        + connection.getCatalog());
            }

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}