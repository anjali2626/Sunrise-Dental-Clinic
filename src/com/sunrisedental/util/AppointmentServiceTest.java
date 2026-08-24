package com.sunrisedental.util;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;

import java.util.List;

public class AppointmentServiceTest {

    public static void main(String[] args) {

        try {

            AppointmentService service =
                    new AppointmentService();

            System.out.println(
                    "Testing Appointment Service...");

            List<Appointment> appointments =
                    service.getAllAppointments();

            System.out.println(
                    "Total appointments: "
                    + appointments.size());

            if (!appointments.isEmpty()) {

                String appointmentNumber =
                        appointments.get(0)
                                .getAppointmentNumber();

                Appointment appointment =
                        service.getAppointmentByNumber(
                                appointmentNumber);

                if (appointment != null) {

                    System.out.println(
                            "Appointment found: "
                            + appointment
                                    .getAppointmentNumber());
                }
            }

            System.out.println(
                    "Appointment service test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Appointment service test failed!");

            e.printStackTrace();
        }
    }
}