package com.sunrisedental.util;

import com.sunrisedental.model.Patient;
import com.sunrisedental.repository.PatientRepository;

import java.util.List;

public class PatientRepositoryTest {

    public static void main(String[] args) {

        try {

            PatientRepository repository =
                    new PatientRepository();

            System.out.println("Testing Patient Repository...");

            List<Patient> patients =
                    repository.findAll();

            System.out.println(
                    "Total patients: " + patients.size());

            for (Patient patient : patients) {

                System.out.println(
                        patient.getPatientId()
                        + " - "
                        + patient.getPatientName()
                        + " - "
                        + patient.getContactNumber());
            }

            System.out.println(
                    "Patient repository test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Patient repository test failed!");

            e.printStackTrace();
        }
    }
}