package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.DailyAppointmentReport;
import com.sunrisedental.model.TreatmentRevenueReport;
import com.sunrisedental.service.ReportService;
import com.sunrisedental.util.HttpUtil;
import com.sunrisedental.util.JsonUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class ReportController implements HttpHandler {

    private final ReportService reportService;

    public ReportController() {

        this.reportService =
                new ReportService();
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

            if (!"GET".equalsIgnoreCase(method)) {

                HttpUtil.sendError(
                        exchange,
                        405,
                        "Method not allowed.");

                return;
            }

            String path =
                    exchange.getRequestURI()
                            .getPath();

            if (path.equals(
                    "/api/reports/daily-appointments")) {

                handleDailyAppointmentReport(
                        exchange);

                return;
            }

            if (path.equals(
                    "/api/reports/treatment-revenue")) {

                handleTreatmentRevenueReport(
                        exchange);

                return;
            }

            HttpUtil.sendError(
                    exchange,
                    404,
                    "Report endpoint not found.");

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

    private void handleDailyAppointmentReport(
            HttpExchange exchange)
            throws Exception {

        String dateValue =
                getQueryParameter(
                        exchange,
                        "date");

        if (dateValue == null ||
                dateValue.isEmpty()) {

            throw new IllegalArgumentException(
                    "Date parameter is required.");
        }

        LocalDate appointmentDate =
                LocalDate.parse(dateValue);

        List<DailyAppointmentReport> reports =
                reportService
                        .getDailyAppointmentReport(
                                appointmentDate);

        HttpUtil.sendResponse(
                exchange,
                200,
                JsonUtil
                        .dailyAppointmentReportsToJson(
                                reports));
    }

    private void handleTreatmentRevenueReport(
            HttpExchange exchange)
            throws Exception {

        String period =
                getQueryParameter(
                        exchange,
                        "period");

        if (period == null ||
                period.isEmpty()) {

            throw new IllegalArgumentException(
                    "Period parameter is required.");
        }

        List<TreatmentRevenueReport> reports =
                reportService
                        .getTreatmentRevenueReport(
                                period);

        HttpUtil.sendResponse(
                exchange,
                200,
                JsonUtil
                        .treatmentRevenueReportsToJson(
                                reports));
    }

    private String getQueryParameter(
            HttpExchange exchange,
            String parameterName) {

        String query =
                exchange.getRequestURI()
                        .getQuery();

        if (query == null ||
                query.isEmpty()) {

            return null;
        }

        String[] parameters =
                query.split("&");

        for (String parameter : parameters) {

            String[] keyValue =
                    parameter.split("=", 2);

            if (keyValue.length == 2 &&
                    keyValue[0].equals(
                            parameterName)) {

                return URLDecoder.decode(
                        keyValue[1],
                        StandardCharsets.UTF_8);
            }
        }

        return null;
    }
}