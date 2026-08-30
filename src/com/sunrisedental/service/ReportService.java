package com.sunrisedental.service;

import com.sunrisedental.model.DailyAppointmentReport;
import com.sunrisedental.model.TreatmentRevenueReport;
import com.sunrisedental.repository.ReportRepository;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService() {

        this.reportRepository =
                new ReportRepository();
    }

    public List<DailyAppointmentReport>
            getDailyAppointmentReport(
                    LocalDate appointmentDate)
            throws SQLException {

        if (appointmentDate == null) {

            throw new IllegalArgumentException(
                    "Appointment date is required.");
        }

        return reportRepository
                .getDailyAppointmentReport(
                        appointmentDate);
    }

    public List<TreatmentRevenueReport>
            getTreatmentRevenueReport(
                    String period)
            throws SQLException {

        if (period == null ||
                period.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Report period is required.");
        }

        LocalDate today =
                LocalDate.now();

        LocalDate startDate;
        LocalDate endDate = today;

        switch (period.toLowerCase().trim()) {

            case "today":

                startDate = today;
                break;

            case "week":

                startDate =
                        today.with(
                                DayOfWeek.MONDAY);
                break;

            case "month":

                startDate =
                        today.withDayOfMonth(1);
                break;

            default:

                throw new IllegalArgumentException(
                        "Invalid report period. "
                        + "Use today, week, or month.");
        }

        return reportRepository
                .getTreatmentRevenueReport(
                        startDate,
                        endDate);
    }
}