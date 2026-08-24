package com.sunrisedental.util;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.repository.DentistRepository;

import java.util.List;

public class DentistRepositoryTest {

    public static void main(String[] args) {

        try {

            DentistRepository repository =
                    new DentistRepository();

            System.out.println("Testing Dentist Repository...");

            List<Dentist> dentists =
                    repository.findAll();

            System.out.println(
                    "Total dentists: " + dentists.size());

            for (Dentist dentist : dentists) {

                System.out.println(
                        dentist.getDentistId()
                        + " - "
                        + dentist.getDentistName()
                        + " - "
                        + dentist.getSpecialization()
                        + " - Active: "
                        + dentist.isActive());
            }

            System.out.println(
                    "Dentist repository test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Dentist repository test failed!");

            e.printStackTrace();
        }
    }
}