package com.sunrisedental.util;

import com.sunrisedental.model.Patient;
import com.sunrisedental.service.PatientService;

import java.util.List;

public class PatientServiceTest {

    public static void main(String[] args) {

        try {

            PatientService service =
                    new PatientService();

            System.out.println(
                    "Testing Patient Service...");

            List<Patient> patients =
                    service.getAllPatients();

            System.out.println(
                    "Total patients: "
                    + patients.size());

            System.out.println(
                    "Patient service test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Patient service test failed!");

            e.printStackTrace();
        }
    }
}