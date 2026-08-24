package com.sunrisedental.util;

import com.sunrisedental.model.User;
import com.sunrisedental.service.UserService;

public class UserServiceTest {

    public static void main(String[] args) {

        try {

            UserService service =
                    new UserService();

            System.out.println(
                    "Testing User Service...");

            
            User user =
                    service.findUserByUsername("admin");

            if (user != null) {

                System.out.println(
                        "User found: "
                        + user.getUsername());

                System.out.println(
                        "Full name: "
                        + user.getFullName());

                System.out.println(
                        "Role: "
                        + user.getRole());

            } else {

                System.out.println(
                        "User not found.");
            }

            System.out.println(
                    "User service test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "User service test failed!");

            e.printStackTrace();
        }
    }
}