package com.sunrisedental.service;

import com.sunrisedental.dto.DashboardDTO;
import com.sunrisedental.repository.DashboardRepository;

public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService() {
        this.dashboardRepository =
                new DashboardRepository();
    }

    public DashboardDTO getDashboardData()
            throws Exception {

        int totalPatients =
                dashboardRepository.getTotalPatients();

        int todaysAppointments =
                dashboardRepository.getTodaysAppointments();

        int activeDentists =
                dashboardRepository.getActiveDentists();

        double todaysRevenue =
                dashboardRepository.getTodaysRevenue();

        return new DashboardDTO(
                totalPatients,
                todaysAppointments,
                activeDentists,
                todaysRevenue,
                dashboardRepository
                        .getTodaysAppointmentList()
        );
    }
}