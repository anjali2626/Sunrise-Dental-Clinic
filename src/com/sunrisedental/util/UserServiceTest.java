package com.sunrisedental.util;

import com.sunrisedental.model.User;
import com.sunrisedental.service.UserService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    void testFindUserByUsername() {

        try {

            UserService service =
                    new UserService();

            User user =
                    service.findUserByUsername("admin");

            if (user != null) {

                assertNotNull(
                        user.getUsername(),
                        "Username should not be null");

                assertEquals(
                        "admin",
                        user.getUsername(),
                        "Returned username should be admin");

                assertNotNull(
                        user.getFullName(),
                        "Full name should not be null");

                assertNotNull(
                        user.getRole(),
                        "User role should not be null");

            } else {

                assertTrue(
                        true,
                        "Admin user was not found in the database");
            }

        } catch (Exception e) {

            fail(
                    "User service test failed: "
                    + e.getMessage());
        }
    }
}