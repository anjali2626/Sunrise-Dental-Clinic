package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.Patient;
import com.sunrisedental.service.PatientService;
import com.sunrisedental.util.HttpUtil;
import com.sunrisedental.util.JsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PatientController
        implements HttpHandler {

    private final PatientService patientService;

    public PatientController() {
        this.patientService =
                new PatientService();
    }

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        try {

            String method =
                    exchange.getRequestMethod();

            if ("OPTIONS".equalsIgnoreCase(method)) {

                HttpUtil.handleOptions(exchange);
                return;
            }

            if ("GET".equalsIgnoreCase(method)) {

                handleGet(exchange);
                return;
            }

            if ("POST".equalsIgnoreCase(method)) {

                handlePost(exchange);
                return;
            }

            HttpUtil.sendError(
                    exchange,
                    405,
                    "Method not allowed.");

        } catch (Exception e) {

            e.printStackTrace();

            HttpUtil.sendError(
                    exchange,
                    500,
                    "Internal server error.");
        }
    }

    private void handleGet(
            HttpExchange exchange)
            throws Exception {

        String path =
                exchange.getRequestURI()
                        .getPath();

        String basePath =
                "/api/patients";

        if (path.equals(basePath)) {

            List<Patient> patients =
                    patientService
                            .getAllPatients();

            HttpUtil.sendResponse(
                    exchange,
                    200,
                    JsonUtil.patientsToJson(
                            patients));

            return;
        }

        String idText =
                path.substring(
                        basePath.length() + 1);

        int patientId =
                Integer.parseInt(idText);

        Patient patient =
                patientService
                        .getPatientById(
                                patientId);

        if (patient == null) {

            HttpUtil.sendError(
                    exchange,
                    404,
                    "Patient not found.");

            return;
        }

        HttpUtil.sendResponse(
                exchange,
                200,
                JsonUtil.patientToJson(
                        patient));
    }

    private void handlePost(
            HttpExchange exchange)
            throws Exception {

        String body =
                HttpUtil.readRequestBody(
                        exchange);

        Map<String, String> data =
                HttpUtil.parseFormData(body);

        String patientName =
                data.get("patientName");

        String address =
                data.get("address");

        String contactNumber =
                data.get("contactNumber");

        Patient patient =
                patientService.registerPatient(
                        patientName,
                        address,
                        contactNumber);

        HttpUtil.sendResponse(
                exchange,
                201,
                JsonUtil.patientToJson(
                        patient));
    }
}