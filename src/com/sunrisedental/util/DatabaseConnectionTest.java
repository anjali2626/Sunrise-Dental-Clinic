package com.sunrisedental.util;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @Test
    void testDatabaseConnection() {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            assertNotNull(
                    connection,
                    "Database connection should not be null");

            assertFalse(
                    connection.isClosed(),
                    "Database connection should be open");

            assertNotNull(
                    connection.getCatalog(),
                    "Database catalog should not be null");

        } catch (Exception e) {

            fail(
                    "Database connection test failed: "
                    + e.getMessage());
        }
    }
}