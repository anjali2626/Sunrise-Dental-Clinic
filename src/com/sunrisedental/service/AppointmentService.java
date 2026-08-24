package com.sunrisedental.service;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.repository.AppointmentRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService() {
        this.appointmentRepository =
                new AppointmentRepository();
    }

    public Appointment registerAppointment(
            String appointmentNumber,
            int patientId,
            int dentistId,
            int treatmentId,
            LocalDate appointmentDate,
            LocalTime appointmentTime)
            throws SQLException {

        validateAppointmentData(
                appointmentNumber,
                patientId,
                dentistId,
                treatmentId,
                appointmentDate,
                appointmentTime);

        if (appointmentRepository.findByAppointmentNumber(
                appointmentNumber.trim()) != null) {

            throw new IllegalArgumentException(
                    "Appointment number already exists.");
        }

        if (!appointmentRepository.isDentistAvailable(
                dentistId,
                appointmentDate,
                appointmentTime)) {

            throw new IllegalArgumentException(
                    "The selected dentist is already booked "
                    + "for this date and time.");
        }

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentNumber(
                appointmentNumber.trim());

        appointment.setPatientId(patientId);
        appointment.setDentistId(dentistId);
        appointment.setTreatmentId(treatmentId);

        appointment.setAppointmentDate(
                appointmentDate);

        appointment.setAppointmentTime(
                appointmentTime);

        appointment.setStatus("SCHEDULED");

        return appointmentRepository.create(
                appointment);
    }

    public Appointment getAppointmentByNumber(
            String appointmentNumber)
            throws SQLException {

        if (appointmentNumber == null ||
                appointmentNumber.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Appointment number is required.");
        }

        return appointmentRepository
                .findByAppointmentNumber(
                        appointmentNumber.trim());
    }

    public Appointment getAppointmentById(
            int appointmentId) throws SQLException {

        if (appointmentId <= 0) {
            throw new IllegalArgumentException(
                    "Appointment ID must be greater than zero.");
        }

        return appointmentRepository
                .findById(appointmentId);
    }

    public List<Appointment> getAllAppointments()
            throws SQLException {

        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByDate(
            LocalDate date) throws SQLException {

        if (date == null) {
            throw new IllegalArgumentException(
                    "Appointment date is required.");
        }

        return appointmentRepository.findByDate(date);
    }

    public boolean updateAppointment(
            int appointmentId,
            String appointmentNumber,
            int patientId,
            int dentistId,
            int treatmentId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            String status)
            throws SQLException {

        if (appointmentId <= 0) {
            throw new IllegalArgumentException(
                    "Appointment ID must be greater than zero.");
        }

        validateAppointmentData(
                appointmentNumber,
                patientId,
                dentistId,
                treatmentId,
                appointmentDate,
                appointmentTime);

        if (status == null ||
                status.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Appointment status is required.");
        }

        Appointment existing =
                appointmentRepository
                        .findById(appointmentId);

        if (existing == null) {
            return false;
        }

        Appointment sameNumber =
                appointmentRepository
                        .findByAppointmentNumber(
                                appointmentNumber.trim());

        if (sameNumber != null &&
                sameNumber.getAppointmentId()
                        != appointmentId) {

            throw new IllegalArgumentException(
                    "Appointment number already exists.");
        }

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentId(appointmentId);

        appointment.setAppointmentNumber(
                appointmentNumber.trim());

        appointment.setPatientId(patientId);
        appointment.setDentistId(dentistId);
        appointment.setTreatmentId(treatmentId);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus(status.trim());

        return appointmentRepository
                .update(appointment);
    }

    public boolean cancelAppointment(
            int appointmentId) throws SQLException {

        Appointment appointment =
                appointmentRepository
                        .findById(appointmentId);

        if (appointment == null) {
            return false;
        }

        return appointmentRepository.updateStatus(
                appointmentId,
                "CANCELLED");
    }

    public boolean isDentistAvailable(
            int dentistId,
            LocalDate date,
            LocalTime time)
            throws SQLException {

        if (dentistId <= 0) {
            throw new IllegalArgumentException(
                    "Dentist ID must be greater than zero.");
        }

        if (date == null) {
            throw new IllegalArgumentException(
                    "Appointment date is required.");
        }

        if (time == null) {
            throw new IllegalArgumentException(
                    "Appointment time is required.");
        }

        return appointmentRepository
                .isDentistAvailable(
                        dentistId,
                        date,
                        time);
    }

    private void validateAppointmentData(
            String appointmentNumber,
            int patientId,
            int dentistId,
            int treatmentId,
            LocalDate appointmentDate,
            LocalTime appointmentTime) {

        if (appointmentNumber == null ||
                appointmentNumber.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Appointment number is required.");
        }

        if (patientId <= 0) {
            throw new IllegalArgumentException(
                    "A valid patient must be selected.");
        }

        if (dentistId <= 0) {
            throw new IllegalArgumentException(
                    "A valid dentist must be selected.");
        }

        if (treatmentId <= 0) {
            throw new IllegalArgumentException(
                    "A valid treatment must be selected.");
        }

        if (appointmentDate == null) {
            throw new IllegalArgumentException(
                    "Appointment date is required.");
        }

        if (appointmentDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Appointment date cannot be in the past.");
        }

        if (appointmentTime == null) {
            throw new IllegalArgumentException(
                    "Appointment time is required.");
        }
    }
}