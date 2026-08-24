package com.sunrisedental.util;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private HttpUtil() {
    }

    public static void addCorsHeaders(HttpExchange exchange) {

        Headers headers = exchange.getResponseHeaders();

        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");
        headers.set("Access-Control-Allow-Headers",
                "Content-Type");
    }

    public static void handleOptions(HttpExchange exchange)
            throws IOException {

        addCorsHeaders(exchange);

        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    public static String readRequestBody(
            HttpExchange exchange) throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        return new String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8);
    }

    public static Map<String, String> parseFormData(
            String body) {

        Map<String, String> data =
                new HashMap<>();

        if (body == null || body.isEmpty()) {
            return data;
        }

        String[] pairs = body.split("&");

        for (String pair : pairs) {

            String[] keyValue =
                    pair.split("=", 2);

            String key =
                    URLDecoder.decode(
                            keyValue[0],
                            StandardCharsets.UTF_8);

            String value = "";

            if (keyValue.length > 1) {

                value =
                        URLDecoder.decode(
                                keyValue[1],
                                StandardCharsets.UTF_8);
            }

            data.put(key, value);
        }

        return data;
    }

    public static String getParameter(
            Map<String, String> data,
            String parameterName) {

        return data.get(parameterName);
    }

    public static void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response)
            throws IOException {

        addCorsHeaders(exchange);

        exchange.getResponseHeaders()
                .set("Content-Type",
                        "application/json; charset=UTF-8");

        byte[] responseBytes =
                response.getBytes(
                        StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                statusCode,
                responseBytes.length);

        exchange.getResponseBody()
                .write(responseBytes);

        exchange.close();
    }

    public static void sendError(
            HttpExchange exchange,
            int statusCode,
            String message)
            throws IOException {

        String response =
                "{\"success\":false,\"message\":\""
                + escapeJson(message)
                + "\"}";

        sendResponse(
                exchange,
                statusCode,
                response);
    }

    public static String escapeJson(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}