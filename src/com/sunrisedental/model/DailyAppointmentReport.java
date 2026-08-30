package com.sunrisedental.model;

import java.time.LocalTime;

public class DailyAppointmentReport {

    private String appointmentNumber;
    private String patientName;
    private String dentistName;
    private String treatmentName;
    private LocalTime appointmentTime;
    private String status;

    public DailyAppointmentReport() {
    }

    public DailyAppointmentReport(
            String appointmentNumber,
            String patientName,
            String dentistName,
            String treatmentName,
            LocalTime appointmentTime,
            String status) {

        this.appointmentNumber = appointmentNumber;
        this.patientName = patientName;
        this.dentistName = dentistName;
        this.treatmentName = treatmentName;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(
            String appointmentNumber) {

        this.appointmentNumber =
                appointmentNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(
            String patientName) {

        this.patientName =
                patientName;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(
            String dentistName) {

        this.dentistName =
                dentistName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(
            String treatmentName) {

        this.treatmentName =
                treatmentName;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(
            LocalTime appointmentTime) {

        this.appointmentTime =
                appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {

        this.status = status;
    }
}