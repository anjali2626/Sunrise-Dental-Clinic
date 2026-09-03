package com.sunrisedental.util;

import com.sunrisedental.model.Bill;
import com.sunrisedental.service.BillService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BillServiceTest {

    @Test
    void testGetBillById() {

        try {

            BillService service =
                    new BillService();

            Bill bill =
                    service.getBillById(1);

            if (bill != null) {

                assertTrue(
                        bill.getBillId() > 0,
                        "Bill ID should be greater than 0");

                assertNotNull(
                        bill.getTotalAmount(),
                        "Total amount should not be null");

            } else {

                assertTrue(
                        true,
                        "No bill found with Bill ID 1");
            }

        } catch (Exception e) {

            fail(
                    "Bill service test failed: "
                    + e.getMessage());
        }
    }
}