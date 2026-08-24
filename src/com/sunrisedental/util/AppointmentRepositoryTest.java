package com.sunrisedental.util;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.repository.AppointmentRepository;

import java.util.List;

public class AppointmentRepositoryTest {

    public static void main(String[] args) {

        try {

            AppointmentRepository repository =
                    new AppointmentRepository();

            System.out.println(
                    "Testing Appointment Repository...");

            List<Appointment> appointments =
                    repository.findAll();

            System.out.println(
                    "Total appointments: "
                    + appointments.size());

            for (Appointment appointment : appointments) {

                System.out.println(
                        appointment.getAppointmentId()
                        + " - "
                        + appointment.getAppointmentNumber()
                        + " - Patient ID: "
                        + appointment.getPatientId()
                        + " - Dentist ID: "
                        + appointment.getDentistId()
                        + " - Date: "
                        + appointment.getAppointmentDate()
                        + " - Time: "
                        + appointment.getAppointmentTime()
                        + " - Status: "
                        + appointment.getStatus());
            }

            System.out.println(
                    "Appointment repository test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Appointment repository test failed!");

            e.printStackTrace();
        }
    }
}