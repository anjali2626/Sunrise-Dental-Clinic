package com.sunrisedental.util;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.service.DentistService;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DentistServiceTest {

    @Test
    void testGetActiveDentists() {

        try {

            DentistService service =
                    new DentistService();

            List<Dentist> dentists =
                    service.getActiveDentists();

            assertNotNull(
                    dentists,
                    "Active dentist list should not be null");

            assertTrue(
                    dentists.size() >= 0,
                    "Active dentist list should contain zero or more dentists");

            for (Dentist dentist : dentists) {

                assertNotNull(
                        dentist,
                        "Dentist object should not be null");

                assertTrue(
                        dentist.isActive(),
                        "Returned dentist should be active");

            }

        } catch (Exception e) {

            fail(
                    "Dentist service test failed: "
                    + e.getMessage());
        }
    }
}