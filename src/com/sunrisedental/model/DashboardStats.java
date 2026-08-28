package com.sunrisedental.model;

import java.math.BigDecimal;

public class DashboardStats {

    private int totalPatients;

    private int todayAppointments;

    private int activeDentists;

    private BigDecimal todayRevenue;

    public DashboardStats() {
    }

    public DashboardStats(
            int totalPatients,
            int todayAppointments,
            int activeDentists,
            BigDecimal todayRevenue) {

        this.totalPatients = totalPatients;
        this.todayAppointments = todayAppointments;
        this.activeDentists = activeDentists;
        this.todayRevenue = todayRevenue;
    }

    public int getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }

    public int getTodayAppointments() {
        return todayAppointments;
    }

    public void setTodayAppointments(int todayAppointments) {
        this.todayAppointments = todayAppointments;
    }

    public int getActiveDentists() {
        return activeDentists;
    }

    public void setActiveDentists(int activeDentists) {
        this.activeDentists = activeDentists;
    }

    public BigDecimal getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(BigDecimal todayRevenue) {
        this.todayRevenue = todayRevenue;
    }
}