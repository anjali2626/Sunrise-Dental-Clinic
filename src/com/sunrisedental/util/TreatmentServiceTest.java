package com.sunrisedental.util;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.service.TreatmentService;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreatmentServiceTest {

    @Test
    void testGetActiveTreatments() {

        try {

            TreatmentService service =
                    new TreatmentService();

            List<Treatment> treatments =
                    service.getActiveTreatments();

            assertNotNull(
                    treatments,
                    "Active treatment list should not be null");

            assertTrue(
                    treatments.size() >= 0,
                    "Active treatment list should contain zero or more treatments");

            for (Treatment treatment : treatments) {

                assertNotNull(
                        treatment,
                        "Treatment object should not be null");

                assertTrue(
                        treatment.isActive(),
                        "Returned treatment should be active");
            }

        } catch (Exception e) {

            fail(
                    "Treatment service test failed: "
                    + e.getMessage());
        }
    }
}