package com.sunrisedental.util;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.repository.DentistRepository;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DentistRepositoryTest {

    @Test
    void testFindAllDentists() {

        try {

            DentistRepository repository =
                    new DentistRepository();

            List<Dentist> dentists =
                    repository.findAll();

            assertNotNull(
                    dentists,
                    "Dentist list should not be null");

            assertTrue(
                    dentists.size() >= 0,
                    "Dentist list should contain zero or more dentists");

            for (Dentist dentist : dentists) {

                assertNotNull(
                        dentist,
                        "Dentist object should not be null");

                assertTrue(
                        dentist.getDentistId() > 0,
                        "Dentist ID should be greater than 0");

                assertNotNull(
                        dentist.getDentistName(),
                        "Dentist name should not be null");

            }

        } catch (Exception e) {

            fail(
                    "Dentist repository test failed: "
                    + e.getMessage());
        }
    }
}