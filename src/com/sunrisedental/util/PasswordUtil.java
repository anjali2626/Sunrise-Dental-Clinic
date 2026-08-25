package com.sunrisedental.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private PasswordUtil() {
        // Utility class
    }

    public static String hashPassword(String password) {

        if (password == null) {
            throw new IllegalArgumentException(
                    "Password cannot be null.");
        }

        try {

            MessageDigest messageDigest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    messageDigest.digest(
                            password.getBytes(
                                    StandardCharsets.UTF_8));

            StringBuilder hexString =
                    new StringBuilder();

            for (byte b : hash) {

                String hex =
                        Integer.toHexString(
                                0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException(
                    "SHA-256 algorithm is not available.",
                    e);
        }
    }

    public static boolean validatePassword(
            String plainPassword,
            String storedHash) {

        if (plainPassword == null ||
                storedHash == null) {

            return false;
        }

        String hashedPassword =
                hashPassword(plainPassword);

        return MessageDigest.isEqual(
                hashedPassword.getBytes(
                        StandardCharsets.UTF_8),
                storedHash.getBytes(
                        StandardCharsets.UTF_8));
    }
}