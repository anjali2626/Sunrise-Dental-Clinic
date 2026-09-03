package com.sunrisedental.util;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.repository.AppointmentRepository;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentRepositoryTest {

    @Test
    void testFindAllAppointments() {

        try {

            AppointmentRepository repository =
                    new AppointmentRepository();

            List<Appointment> appointments =
                    repository.findAll();

            assertNotNull(
                    appointments,
                    "Appointment list should not be null");

            assertTrue(
                    appointments.size() >= 0,
                    "Appointment list should contain zero or more appointments");

            for (Appointment appointment : appointments) {

                assertNotNull(
                        appointment,
                        "Appointment object should not be null");

                assertTrue(
                        appointment.getAppointmentId() > 0,
                        "Appointment ID should be greater than 0");

                assertNotNull(
                        appointment.getAppointmentNumber(),
                        "Appointment number should not be null");

                assertTrue(
                        appointment.getPatientId() > 0,
                        "Patient ID should be greater than 0");

                assertTrue(
                        appointment.getDentistId() > 0,
                        "Dentist ID should be greater than 0");

                assertNotNull(
                        appointment.getAppointmentDate(),
                        "Appointment date should not be null");

                assertNotNull(
                        appointment.getAppointmentTime(),
                        "Appointment time should not be null");

                assertNotNull(
                        appointment.getStatus(),
                        "Appointment status should not be null");
            }

        } catch (Exception e) {

            fail(
                    "Appointment repository test failed: "
                    + e.getMessage());
        }
    }
}