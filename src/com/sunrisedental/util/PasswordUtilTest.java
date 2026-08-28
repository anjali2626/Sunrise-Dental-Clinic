package com.sunrisedental.util;

public class PasswordUtilTest {

    public static void main(String[] args) {

        System.out.println(
                "========================================");

        System.out.println(
                "Password Hashing and Validation Test");

        System.out.println(
                "========================================");


        /* =================================================
           ADMIN USER
           ================================================= */

        String adminPassword = "admin123";

        String adminHash =
                PasswordUtil.hashPassword(
                        adminPassword);

        System.out.println();

        System.out.println(
                "Admin password: " + adminPassword);

        System.out.println(
                "Admin generated hash: " + adminHash);


        boolean adminResult =
                PasswordUtil.validatePassword(
                        adminPassword,
                        adminHash);

        System.out.println(
                "Admin validation result: "
                + adminResult);


        /* =================================================
           RECEPTION USER
           ================================================= */

        String receptionPassword =
                "Reception@123";

        String receptionHash =
                PasswordUtil.hashPassword(
                        receptionPassword);

        System.out.println();

        System.out.println(
                "Reception password: "
                + receptionPassword);

        System.out.println(
                "Reception generated hash: "
                + receptionHash);


        boolean receptionResult =
                PasswordUtil.validatePassword(
                        receptionPassword,
                        receptionHash);

        System.out.println(
                "Reception validation result: "
                + receptionResult);


        /* =================================================
           STAFF01 USER
           ================================================= */

        String staff01Password =
                "Staff01@123";

        String staff01Hash =
                PasswordUtil.hashPassword(
                        staff01Password);

        System.out.println();

        System.out.println(
                "Staff01 password: "
                + staff01Password);

        System.out.println(
                "Staff01 generated hash: "
                + staff01Hash);


        boolean staff01Result =
                PasswordUtil.validatePassword(
                        staff01Password,
                        staff01Hash);

        System.out.println(
                "Staff01 validation result: "
                + staff01Result);


        /* =================================================
           STAFF02 USER
           ================================================= */

        String staff02Password =
                "Staff02@123";

        String staff02Hash =
                PasswordUtil.hashPassword(
                        staff02Password);

        System.out.println();

        System.out.println(
                "Staff02 password: "
                + staff02Password);

        System.out.println(
                "Staff02 generated hash: "
                + staff02Hash);


        boolean staff02Result =
                PasswordUtil.validatePassword(
                        staff02Password,
                        staff02Hash);

        System.out.println(
                "Staff02 validation result: "
                + staff02Result);


        /* =================================================
           WRONG PASSWORD TEST
           ================================================= */

        boolean wrongPasswordResult =
                PasswordUtil.validatePassword(
                        "WrongPassword",
                        receptionHash);

        System.out.println();

        System.out.println(
                "Wrong password validation result: "
                + wrongPasswordResult);


        System.out.println();

        System.out.println(
                "========================================");

        System.out.println(
                "Password utility testing completed.");

        System.out.println(
                "========================================");
    }
}