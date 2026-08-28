package com.sunrisedental.dto;

import java.util.List;

public class DashboardDTO {

    private int totalPatients;
    private int todaysAppointments;
    private int activeDentists;
    private double todaysRevenue;

    private List<TodaysAppointmentDTO> appointments;

    public DashboardDTO() {
    }

    public DashboardDTO(
            int totalPatients,
            int todaysAppointments,
            int activeDentists,
            double todaysRevenue,
            List<TodaysAppointmentDTO> appointments) {

        this.totalPatients = totalPatients;
        this.todaysAppointments = todaysAppointments;
        this.activeDentists = activeDentists;
        this.todaysRevenue = todaysRevenue;
        this.appointments = appointments;
    }

    public int getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }

    public int getTodaysAppointments() {
        return todaysAppointments;
    }

    public void setTodaysAppointments(int todaysAppointments) {
        this.todaysAppointments = todaysAppointments;
    }

    public int getActiveDentists() {
        return activeDentists;
    }

    public void setActiveDentists(int activeDentists) {
        this.activeDentists = activeDentists;
    }

    public double getTodaysRevenue() {
        return todaysRevenue;
    }

    public void setTodaysRevenue(double todaysRevenue) {
        this.todaysRevenue = todaysRevenue;
    }

    public List<TodaysAppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(
            List<TodaysAppointmentDTO> appointments) {

        this.appointments = appointments;
    }
}