package com.sunrisedental.util;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.service.TreatmentService;

import java.util.List;

public class TreatmentServiceTest {

    public static void main(String[] args) {

        try {

            TreatmentService service =
                    new TreatmentService();

            System.out.println(
                    "Testing Treatment Service...");

            List<Treatment> treatments =
                    service.getActiveTreatments();

            System.out.println(
                    "Active treatments: "
                    + treatments.size());

            System.out.println(
                    "Treatment service test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Treatment service test failed!");

            e.printStackTrace();
        }
    }
}