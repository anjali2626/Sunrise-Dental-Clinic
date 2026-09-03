package com.sunrisedental.util;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceTest {

    @Test
    void testGetAllAppointments() {

        try {

            AppointmentService service =
                    new AppointmentService();

            List<Appointment> appointments =
                    service.getAllAppointments();

            assertNotNull(
                    appointments,
                    "Appointment list should not be null");

            assertTrue(
                    appointments.size() >= 0,
                    "Appointment list should contain zero or more appointments");

        } catch (Exception e) {

            fail(
                    "Get all appointments test failed: "
                    + e.getMessage());
        }
    }

    @Test
    void testGetAppointmentByNumber() {

        try {

            AppointmentService service =
                    new AppointmentService();

            List<Appointment> appointments =
                    service.getAllAppointments();

            assertNotNull(
                    appointments,
                    "Appointment list should not be null");

            if (!appointments.isEmpty()) {

                String appointmentNumber =
                        appointments.get(0)
                                .getAppointmentNumber();

                assertNotNull(
                        appointmentNumber,
                        "Appointment number should not be null");

                Appointment appointment =
                        service.getAppointmentByNumber(
                                appointmentNumber);

                assertNotNull(
                        appointment,
                        "Appointment should be found by appointment number");

                assertEquals(
                        appointmentNumber,
                        appointment.getAppointmentNumber(),
                        "Returned appointment number should match the searched number");
            }

        } catch (Exception e) {

            fail(
                    "Get appointment by number test failed: "
                    + e.getMessage());
        }
    }
}