package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.util.HttpUtil;
import com.sunrisedental.util.JsonUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AppointmentController
        implements HttpHandler {

    private final AppointmentService appointmentService;

    public AppointmentController() {
        this.appointmentService =
                new AppointmentService();
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

            if ("PUT".equalsIgnoreCase(method)) {

                handlePut(exchange);
                return;
            }

            if ("DELETE".equalsIgnoreCase(method)) {

                handleDelete(exchange);
                return;
            }

            HttpUtil.sendError(
                    exchange,
                    405,
                    "Method not allowed.");

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

    private void handleGet(
            HttpExchange exchange)
            throws Exception {

        String path =
                exchange.getRequestURI()
                        .getPath();

        String basePath =
                "/api/appointments";

        if (path.equals(basePath)) {

            List<Appointment> appointments =
                    appointmentService
                            .getAllAppointments();

            HttpUtil.sendResponse(
                    exchange,
                    200,
                    JsonUtil.appointmentsToJson(
                            appointments));

            return;
        }

        String appointmentNumber =
                path.substring(
                        basePath.length() + 1);

        Appointment appointment =
                appointmentService
                        .getAppointmentByNumber(
                                appointmentNumber);

        if (appointment == null) {

            HttpUtil.sendError(
                    exchange,
                    404,
                    "Appointment not found.");

            return;
        }

        HttpUtil.sendResponse(
                exchange,
                200,
                JsonUtil.appointmentToJson(
                        appointment));
    }

    private void handlePost(
            HttpExchange exchange)
            throws Exception {

        String body =
                HttpUtil.readRequestBody(
                        exchange);

        Map<String, String> data =
                HttpUtil.parseFormData(body);

        String appointmentNumber =
                data.get("appointmentNumber");

        int patientId =
                Integer.parseInt(
                        data.get("patientId"));

        int dentistId =
                Integer.parseInt(
                        data.get("dentistId"));

        int treatmentId =
                Integer.parseInt(
                        data.get("treatmentId"));

        LocalDate date =
                LocalDate.parse(
                        data.get("appointmentDate"));

        LocalTime time =
                LocalTime.parse(
                        data.get("appointmentTime"));

        Appointment appointment =
                appointmentService
                        .registerAppointment(
                                appointmentNumber,
                                patientId,
                                dentistId,
                                treatmentId,
                                date,
                                time);

        HttpUtil.sendResponse(
                exchange,
                201,
                JsonUtil.appointmentToJson(
                        appointment));
    }

    private void handlePut(
            HttpExchange exchange)
            throws Exception {

        String path =
                exchange.getRequestURI()
                        .getPath();

        String basePath =
                "/api/appointments";

        int appointmentId =
                Integer.parseInt(
                        path.substring(
                                basePath.length() + 1));

        String body =
                HttpUtil.readRequestBody(
                        exchange);

        Map<String, String> data =
                HttpUtil.parseFormData(body);

        String appointmentNumber =
                data.get("appointmentNumber");

        int patientId =
                Integer.parseInt(
                        data.get("patientId"));

        int dentistId =
                Integer.parseInt(
                        data.get("dentistId"));

        int treatmentId =
                Integer.parseInt(
                        data.get("treatmentId"));

        LocalDate date =
                LocalDate.parse(
                        data.get("appointmentDate"));

        LocalTime time =
                LocalTime.parse(
                        data.get("appointmentTime"));

        String status =
                data.get("status");

        boolean updated =
                appointmentService
                        .updateAppointment(
                                appointmentId,
                                appointmentNumber,
                                patientId,
                                dentistId,
                                treatmentId,
                                date,
                                time,
                                status);

        if (!updated) {

            HttpUtil.sendError(
                    exchange,
                    404,
                    "Appointment not found.");

            return;
        }

        HttpUtil.sendResponse(
                exchange,
                200,
                "{\"success\":true,"
                + "\"message\":\"Appointment updated successfully.\"}");
    }

    private void handleDelete(
            HttpExchange exchange)
            throws Exception {

        String path =
                exchange.getRequestURI()
                        .getPath();

        String basePath =
                "/api/appointments";

        int appointmentId =
                Integer.parseInt(
                        path.substring(
                                basePath.length() + 1));

        boolean cancelled =
                appointmentService
                        .cancelAppointment(
                                appointmentId);

        if (!cancelled) {

            HttpUtil.sendError(
                    exchange,
                    404,
                    "Appointment not found.");

            return;
        }

        HttpUtil.sendResponse(
                exchange,
                200,
                "{\"success\":true,"
                + "\"message\":\"Appointment cancelled successfully.\"}");
    }
}