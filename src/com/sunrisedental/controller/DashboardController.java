package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.dto.DashboardDTO;
import com.sunrisedental.dto.TodaysAppointmentDTO;
import com.sunrisedental.service.DashboardService;
import com.sunrisedental.util.HttpUtil;

import java.io.IOException;

public class DashboardController
        implements HttpHandler {

    private final DashboardService dashboardService;

    public DashboardController() {
        this.dashboardService =
                new DashboardService();
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

            DashboardDTO dashboard =
                    dashboardService.getDashboardData();

            StringBuilder json =
                    new StringBuilder();

            json.append("{");

            json.append("\"success\":true,");

            json.append("\"totalPatients\":")
                    .append(dashboard.getTotalPatients())
                    .append(",");

            json.append("\"todaysAppointments\":")
                    .append(dashboard.getTodaysAppointments())
                    .append(",");

            json.append("\"activeDentists\":")
                    .append(dashboard.getActiveDentists())
                    .append(",");

            json.append("\"todaysRevenue\":")
                    .append(String.format(
                            java.util.Locale.US,
                            "%.2f",
                            dashboard.getTodaysRevenue()))
                    .append(",");

            json.append("\"appointments\":[");

            boolean first = true;

            for (TodaysAppointmentDTO appointment :
                    dashboard.getAppointments()) {

                if (!first) {
                    json.append(",");
                }

                json.append("{");

                json.append("\"appointmentNumber\":\"")
                        .append(HttpUtil.escapeJson(
                                appointment
                                        .getAppointmentNumber()))
                        .append("\",");

                json.append("\"patientName\":\"")
                        .append(HttpUtil.escapeJson(
                                appointment
                                        .getPatientName()))
                        .append("\",");

                json.append("\"dentistName\":\"")
                        .append(HttpUtil.escapeJson(
                                appointment
                                        .getDentistName()))
                        .append("\",");

                json.append("\"appointmentTime\":\"")
                        .append(HttpUtil.escapeJson(
                                appointment
                                        .getAppointmentTime()))
                        .append("\",");

                json.append("\"treatmentName\":\"")
                        .append(HttpUtil.escapeJson(
                                appointment
                                        .getTreatmentName()))
                        .append("\",");

                json.append("\"status\":\"")
                        .append(HttpUtil.escapeJson(
                                appointment.getStatus()))
                        .append("\"");

                json.append("}");

                first = false;
            }

            json.append("]");

            json.append("}");

            HttpUtil.sendResponse(
                    exchange,
                    200,
                    json.toString());

        } catch (Exception e) {

            e.printStackTrace();

            HttpUtil.sendError(
                    exchange,
                    500,
                    "Failed to load dashboard data.");
        }
    }
}