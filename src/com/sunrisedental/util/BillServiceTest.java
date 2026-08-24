package com.sunrisedental.util;

import com.sunrisedental.model.Bill;
import com.sunrisedental.service.BillService;

public class BillServiceTest {

    public static void main(String[] args) {

        try {

            BillService service =
                    new BillService();

            System.out.println(
                    "Testing Bill Service...");

            Bill bill =
                    service.getBillById(1);

            if (bill != null) {

                System.out.println(
                        "Bill ID: "
                        + bill.getBillId());

                System.out.println(
                        "Total amount: "
                        + bill.getTotalAmount());

            } else {

                System.out.println(
                        "No bill found with ID 1.");
            }

            System.out.println(
                    "Bill service test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Bill service test failed!");

            e.printStackTrace();
        }
    }
}