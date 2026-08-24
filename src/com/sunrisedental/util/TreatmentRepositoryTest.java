package com.sunrisedental.util;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.repository.TreatmentRepository;

import java.util.List;

public class TreatmentRepositoryTest {

    public static void main(String[] args) {

        try {

            TreatmentRepository repository =
                    new TreatmentRepository();

            System.out.println("Testing Treatment Repository...");

            List<Treatment> treatments =
                    repository.findAll();

            System.out.println(
                    "Total treatments: " + treatments.size());

            for (Treatment treatment : treatments) {

                System.out.println(
                        treatment.getTreatmentId()
                        + " - "
                        + treatment.getTreatmentName()
                        + " - Cost: "
                        + treatment.getTreatmentCost()
                        + " - Active: "
                        + treatment.isActive());
            }

            System.out.println(
                    "Treatment repository test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Treatment repository test failed!");

            e.printStackTrace();
        }
    }
}