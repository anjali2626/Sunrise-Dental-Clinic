package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.service.TreatmentService;
import com.sunrisedental.util.HttpUtil;
import com.sunrisedental.util.JsonUtil;

import java.io.IOException;
import java.util.List;

public class TreatmentController
        implements HttpHandler {

    private final TreatmentService treatmentService;

    public TreatmentController() {
        this.treatmentService =
                new TreatmentService();
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
                    "/api/treatments";

            if (path.equals(basePath)) {

                List<Treatment> treatments =
                        treatmentService
                                .getActiveTreatments();

                HttpUtil.sendResponse(
                        exchange,
                        200,
                        JsonUtil.treatmentsToJson(
                                treatments));

                return;
            }

            int treatmentId =
                    Integer.parseInt(
                            path.substring(
                                    basePath.length()
                                    + 1));

            Treatment treatment =
                    treatmentService
                            .getTreatmentById(
                                    treatmentId);

            if (treatment == null) {

                HttpUtil.sendError(
                        exchange,
                        404,
                        "Treatment not found.");

                return;
            }

            HttpUtil.sendResponse(
                    exchange,
                    200,
                    JsonUtil.treatmentToJson(
                            treatment));

        } catch (Exception e) {

            e.printStackTrace();

            HttpUtil.sendError(
                    exchange,
                    500,
                    "Unable to process treatment request.");
        }
    }
}