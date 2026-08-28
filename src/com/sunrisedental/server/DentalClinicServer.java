package com.sunrisedental.server;

import com.sun.net.httpserver.HttpServer; 

import com.sunrisedental.controller.PatientController;
import com.sunrisedental.controller.DentistController;
import com.sunrisedental.controller.TreatmentController;
import com.sunrisedental.controller.AppointmentController;
import com.sunrisedental.controller.BillController;
import com.sunrisedental.controller.UserController;
import com.sunrisedental.controller.AuthController;
import com.sunrisedental.controller.DashboardController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DentalClinicServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {

        try {

            HttpServer server = HttpServer.create(
                    new InetSocketAddress(PORT), 0);
            
            AuthController authController =
                    new AuthController();

            server.createContext(
                    "/api/auth/login",
                    authController
            );

            
            server.createContext("/", exchange -> {

                String response =
                        "Sunrise Dental Clinic Backend is running!";

                exchange.getResponseHeaders()
                        .set("Content-Type", "text/plain");

                exchange.sendResponseHeaders(
                        200,
                        response.getBytes().length);

                exchange.getResponseBody()
                        .write(response.getBytes());

                exchange.getResponseBody().close();
            });

            
            server.createContext(
                    "/api/patients",
                    new PatientController());

            
            server.createContext(
                    "/api/dentists",
                    new DentistController());

            
            server.createContext(
                    "/api/treatments",
                    new TreatmentController());

            
            server.createContext(
                    "/api/appointments",
                    new AppointmentController());

            
            server.createContext(
                    "/api/bills",
                    new BillController());

            
            server.createContext(
                    "/api/login",
                    new UserController());
            
            server.createContext(
                    "/api/dashboard",
                    new DashboardController());

            
            server.setExecutor(null);

            server.start();

            System.out.println(
                    "Sunrise Dental Clinic Backend started successfully!");

            System.out.println(
                    "Server running at: http://localhost:" + PORT);

            System.out.println(
                    "Patient API: http://localhost:" + PORT
                            + "/api/patients");

            System.out.println(
                    "Dentist API: http://localhost:" + PORT
                            + "/api/dentists");

            System.out.println(
                    "Treatment API: http://localhost:" + PORT
                            + "/api/treatments");

            System.out.println(
                    "Appointment API: http://localhost:" + PORT
                            + "/api/appointments");

            System.out.println(
                    "Bill API: http://localhost:" + PORT
                            + "/api/bills");

            System.out.println(
                    "Login API: http://localhost:" + PORT
                            + "/api/login");
            
            System.out.println(
                    "Dashboard API: http://localhost:" + PORT
                            + "/api/dashboard");

        } catch (IOException e) {

            System.out.println(
                    "Failed to start the server.");

            e.printStackTrace();
        }
    }
}