package com.sunrisedental.util;

public class PasswordUtilTest {

    public static void main(String[] args) {

        String password = "admin123";

        String hash =
                PasswordUtil.hashPassword(password);

        System.out.println(
                "Password: " + password);

        System.out.println(
                "Generated hash: " + hash);

        boolean result =
                PasswordUtil.validatePassword(
                        password,
                        hash);

        System.out.println(
                "Password validation result: "
                + result);
    }
}