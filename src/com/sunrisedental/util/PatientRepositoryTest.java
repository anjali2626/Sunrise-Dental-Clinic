package com.sunrisedental.util;

import com.sunrisedental.model.Patient;
import com.sunrisedental.repository.PatientRepository;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientRepositoryTest {

    @Test
    void testFindAllPatients() {

        try {

            PatientRepository repository =
                    new PatientRepository();

            List<Patient> patients =
                    repository.findAll();

            assertNotNull(
                    patients,
                    "Patient list should not be null");

            assertTrue(
                    patients.size() >= 0,
                    "Patient list should contain zero or more patients");

            for (Patient patient : patients) {

                assertNotNull(
                        patient,
                        "Patient object should not be null");

                assertTrue(
                        patient.getPatientId() > 0,
                        "Patient ID should be greater than 0");

                assertNotNull(
                        patient.getPatientName(),
                        "Patient name should not be null");

                assertNotNull(
                        patient.getContactNumber(),
                        "Patient contact number should not be null");
            }

        } catch (Exception e) {

            fail(
                    "Patient repository test failed: "
                    + e.getMessage());
        }
    }
}