package com.sunrisedental.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.sunrisedental.model.Bill;
import com.sunrisedental.service.BillService;
import com.sunrisedental.util.HttpUtil;
import com.sunrisedental.util.JsonUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BillController
        implements HttpHandler {

    private final BillService billService;

    public BillController() {

        this.billService =
                new BillService();
    }

    @Override
    public void handle(
            HttpExchange exchange)
            throws IOException {

        try {

            String method =
                    exchange.getRequestMethod();

            if ("OPTIONS".equalsIgnoreCase(
                    method)) {

                HttpUtil.handleOptions(
                        exchange);

                return;
            }

            if ("POST".equalsIgnoreCase(
                    method)) {

                handlePost(exchange);

                return;
            }

            if ("GET".equalsIgnoreCase(
                    method)) {

                handleGet(exchange);

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

    private void handlePost(
            HttpExchange exchange)
            throws Exception {

        String body =
                HttpUtil.readRequestBody(
                        exchange);

        Map<String, String> data =
                HttpUtil.parseFormData(
                        body);

        int appointmentId =
                Integer.parseInt(
                        data.get(
                                "appointmentId"));

        BigDecimal consultationFee =
                new BigDecimal(
                        data.get(
                                "consultationFee"));

        Bill bill =
                billService.generateBill(
                        appointmentId,
                        consultationFee);

        HttpUtil.sendResponse(
                exchange,
                201,
                JsonUtil.billToJson(
                        bill));
    }

    private void handleGet(
            HttpExchange exchange)
            throws Exception {

        String path =
                exchange.getRequestURI()
                        .getPath();

        String basePath =
                "/api/bills";
        

        if (path.equals(basePath) ||
                path.equals(basePath + "/")) {

            List<Bill> bills =
                    billService.getAllBills();

            HttpUtil.sendResponse(
                    exchange,
                    200,
                    JsonUtil.billsToJson(
                            bills));

            return;
        }

       

        String billIdText =
                path.substring(
                        basePath.length() + 1);

        int billId =
                Integer.parseInt(
                        billIdText);

        Bill bill =
                billService.getBillById(
                        billId);

        if (bill == null) {

            HttpUtil.sendError(
                    exchange,
                    404,
                    "Bill not found.");

            return;
        }

        HttpUtil.sendResponse(
                exchange,
                200,
                JsonUtil.billToJson(
                        bill));
    }
}