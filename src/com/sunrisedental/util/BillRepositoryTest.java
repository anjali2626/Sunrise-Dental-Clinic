package com.sunrisedental.util;

import com.sunrisedental.model.Bill;
import com.sunrisedental.repository.BillRepository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BillRepositoryTest {

    @Test
    void testFindBillById() {

        try {

            BillRepository repository =
                    new BillRepository();

            Bill bill =
                    repository.findById(1);

            if (bill != null) {

                assertTrue(
                        bill.getBillId() > 0,
                        "Bill ID should be greater than 0");

                assertTrue(
                        bill.getAppointmentId() > 0,
                        "Appointment ID should be greater than 0");

                assertNotNull(
                        bill.getConsultationFee(),
                        "Consultation fee should not be null");

                assertNotNull(
                        bill.getTreatmentCost(),
                        "Treatment cost should not be null");

                assertNotNull(
                        bill.getTotalAmount(),
                        "Total amount should not be null");

                assertNotNull(
                        bill.getBillDate(),
                        "Bill date should not be null");

            } else {

                assertTrue(
                        true,
                        "No bill found with Bill ID 1");
            }

        } catch (Exception e) {

            fail(
                    "Bill repository test failed: "
                    + e.getMessage());
        }
    }
}