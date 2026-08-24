package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.service.DentistService;
import com.sunrisedental.util.HttpUtil;
import com.sunrisedental.util.JsonUtil;

import java.io.IOException;
import java.util.List;

public class DentistController
        implements HttpHandler {

    private final DentistService dentistService;

    public DentistController() {
        this.dentistService =
                new DentistService();
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

            if (!"GET".equalsIgnoreCase(
                    exchange.getRequestMethod())) {

                HttpUtil.sendError(
                        exchange,
                        405,
                        "Only GET method is allowed.");

                return;
            }

            String path =
                    exchange.getRequestURI()
                            .getPath();

            String basePath =
                    "/api/dentists";

            if (path.equals(basePath)) {

                List<Dentist> dentists =
                        dentistService
                                .getActiveDentists();

                HttpUtil.sendResponse(
                        exchange,
                        200,
                        JsonUtil.dentistsToJson(
                                dentists));

                return;
            }

            int dentistId =
                    Integer.parseInt(
                            path.substring(
                                    basePath.length()
                                    + 1));

            Dentist dentist =
                    dentistService
                            .getDentistById(
                                    dentistId);

            if (dentist == null) {

                HttpUtil.sendError(
                        exchange,
                        404,
                        "Dentist not found.");

                return;
            }

            HttpUtil.sendResponse(
                    exchange,
                    200,
                    JsonUtil.dentistToJson(
                            dentist));

        } catch (Exception e) {

            e.printStackTrace();

            HttpUtil.sendError(
                    exchange,
                    500,
                    "Unable to process dentist request.");
        }
    }
}