package com.sunrisedental.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DentalClinicServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(
                    new InetSocketAddress(PORT), 0);

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

            server.setExecutor(null);

            server.start();

            System.out.println(
                    "Sunrise Dental Clinic Backend started successfully!");

            System.out.println(
                    "Server running at: http://localhost:" + PORT);

        } catch (IOException e) {

            System.out.println(
                    "Failed to start the server.");

            e.printStackTrace();
        }
    }
}