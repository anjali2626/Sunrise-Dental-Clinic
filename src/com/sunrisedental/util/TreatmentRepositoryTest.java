package com.sunrisedental.util;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.repository.TreatmentRepository;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreatmentRepositoryTest {

    @Test
    void testFindAllTreatments() {

        try {

            TreatmentRepository repository =
                    new TreatmentRepository();

            List<Treatment> treatments =
                    repository.findAll();

            assertNotNull(
                    treatments,
                    "Treatment list should not be null");

            assertTrue(
                    treatments.size() >= 0,
                    "Treatment list should contain zero or more treatments");

            for (Treatment treatment : treatments) {

                assertNotNull(
                        treatment,
                        "Treatment object should not be null");

                assertTrue(
                        treatment.getTreatmentId() > 0,
                        "Treatment ID should be greater than 0");

                assertNotNull(
                        treatment.getTreatmentName(),
                        "Treatment name should not be null");

                assertNotNull(
                        treatment.getTreatmentCost(),
                        "Treatment cost should not be null");

                assertTrue(
                        treatment.getTreatmentCost().compareTo(
                                java.math.BigDecimal.ZERO) >= 0,
                        "Treatment cost should not be negative");
            }

        } catch (Exception e) {

            fail(
                    "Treatment repository test failed: "
                    + e.getMessage());
        }
    }
}