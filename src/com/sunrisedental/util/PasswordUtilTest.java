package com.sunrisedental.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    void testPasswordHashingAndValidation() {

        try {

            String adminPassword = "admin123";

            String adminHash =
                    PasswordUtil.hashPassword(
                            adminPassword);

            assertNotNull(
                    adminHash,
                    "Admin password hash should not be null");

            assertTrue(
                    PasswordUtil.validatePassword(
                            adminPassword,
                            adminHash),
                    "Admin password validation should succeed");


            String receptionPassword =
                    "Reception@123";

            String receptionHash =
                    PasswordUtil.hashPassword(
                            receptionPassword);

            assertNotNull(
                    receptionHash,
                    "Reception password hash should not be null");

            assertTrue(
                    PasswordUtil.validatePassword(
                            receptionPassword,
                            receptionHash),
                    "Reception password validation should succeed");


            String staff01Password =
                    "Staff01@123";

            String staff01Hash =
                    PasswordUtil.hashPassword(
                            staff01Password);

            assertNotNull(
                    staff01Hash,
                    "Staff01 password hash should not be null");

            assertTrue(
                    PasswordUtil.validatePassword(
                            staff01Password,
                            staff01Hash),
                    "Staff01 password validation should succeed");


            String staff02Password =
                    "Staff02@123";

            String staff02Hash =
                    PasswordUtil.hashPassword(
                            staff02Password);

            assertNotNull(
                    staff02Hash,
                    "Staff02 password hash should not be null");

            assertTrue(
                    PasswordUtil.validatePassword(
                            staff02Password,
                            staff02Hash),
                    "Staff02 password validation should succeed");


            assertFalse(
                    PasswordUtil.validatePassword(
                            "WrongPassword",
                            receptionHash),
                    "Wrong password should not be validated successfully");

        } catch (Exception e) {

            fail(
                    "Password utility test failed: "
                    + e.getMessage());
        }
    }
}