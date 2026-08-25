package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.User;
import com.sunrisedental.service.AuthService;
import com.sunrisedental.util.HttpUtil;

import java.io.IOException;
import java.util.Map;

public class AuthController
        implements HttpHandler {

    private final AuthService authService;

    public AuthController() {
        this.authService =
                new AuthService();
    }

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        try {

            if ("OPTIONS".equalsIgnoreCase(
                    exchange.getRequestMethod())) {

                HttpUtil.handleOptions(exchange);
                return;
            }

            if (!"POST".equalsIgnoreCase(
                    exchange.getRequestMethod())) {

                HttpUtil.sendError(
                        exchange,
                        405,
                        "Only POST method is allowed.");

                return;
            }

            String body =
                    HttpUtil.readRequestBody(
                            exchange);

            Map<String, String> data =
                    HttpUtil.parseFormData(body);

            String username =
                    data.get("username");

            String password =
                    data.get("password");

            User user =
                    authService.login(
                            username,
                            password);

            if (user == null) {

                HttpUtil.sendError(
                        exchange,
                        401,
                        "Invalid username or password.");

                return;
            }

            String response =
                    "{"
                    + "\"success\":true,"
                    + "\"message\":\"Login successful\","
                    + "\"userId\":"
                    + user.getUserId()
                    + ","
                    + "\"username\":\""
                    + HttpUtil.escapeJson(
                            user.getUsername())
                    + "\","
                    + "\"fullName\":\""
                    + HttpUtil.escapeJson(
                            user.getFullName())
                    + "\","
                    + "\"role\":\""
                    + HttpUtil.escapeJson(
                            user.getRole())
                    + "\""
                    + "}";

            HttpUtil.sendResponse(
                    exchange,
                    200,
                    response);

        } catch (IllegalArgumentException e) {

            HttpUtil.sendError(
                    exchange,
                    400,
                    e.getMessage());

        } catch (Exception e) {

            e.printStackTrace();

            HttpUtil.sendError(
                    exchange,
                    500,
                    "Internal server error.");
        }
    }
}