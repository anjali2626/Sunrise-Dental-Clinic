package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.User;
import com.sunrisedental.service.UserService;
import com.sunrisedental.util.HttpUtil;

import java.io.IOException;
import java.util.Map;

public class UserController
        implements HttpHandler {

    private final UserService userService;

    public UserController() {
        this.userService =
                new UserService();
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

            User user =
                    userService
                            .findUserByUsername(
                                    username);

            if (user == null) {

                HttpUtil.sendError(
                        exchange,
                        401,
                        "Invalid username or password.");

                return;
            }

            /*
             * Password verification will be completed
             * in the authentication enhancement stage.
             *
             * At this stage, the controller verifies
             * that the requested username exists.
             */

            String response =
                    "{"
                    + "\"success\":true,"
                    + "\"message\":\"User found.\","
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