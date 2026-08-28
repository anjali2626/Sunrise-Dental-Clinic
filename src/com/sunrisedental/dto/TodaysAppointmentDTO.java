package com.sunrisedental.dto;

public class TodaysAppointmentDTO {

    private String appointmentNumber;
    private String patientName;
    private String dentistName;
    private String appointmentTime;
    private String treatmentName;
    private String status;

    public TodaysAppointmentDTO() {
    }

    public TodaysAppointmentDTO(
            String appointmentNumber,
            String patientName,
            String dentistName,
            String appointmentTime,
            String treatmentName,
            String status) {

        this.appointmentNumber = appointmentNumber;
        this.patientName = patientName;
        this.dentistName = dentistName;
        this.appointmentTime = appointmentTime;
        this.treatmentName = treatmentName;
        this.status = status;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}