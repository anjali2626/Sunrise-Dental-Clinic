package com.sunrisedental.util;

import com.sunrisedental.model.Bill;
import com.sunrisedental.repository.BillRepository;

public class BillRepositoryTest {

    public static void main(String[] args) {

        try {

            BillRepository repository =
                    new BillRepository();

            System.out.println(
                    "Testing Bill Repository...");

            Bill bill = repository.findById(1);

            if (bill != null) {

                System.out.println(
                        "Bill ID: "
                        + bill.getBillId());

                System.out.println(
                        "Appointment ID: "
                        + bill.getAppointmentId());

                System.out.println(
                        "Consultation Fee: "
                        + bill.getConsultationFee());

                System.out.println(
                        "Treatment Cost: "
                        + bill.getTreatmentCost());

                System.out.println(
                        "Total Amount: "
                        + bill.getTotalAmount());

                System.out.println(
                        "Bill Date: "
                        + bill.getBillDate());

            } else {

                System.out.println(
                        "No bill found with Bill ID 1.");
            }

            System.out.println(
                    "Bill repository test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Bill repository test failed!");

            e.printStackTrace();
        }
    }
}