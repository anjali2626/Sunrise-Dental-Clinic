package com.sunrisedental.repository;

import com.sunrisedental.model.DailyAppointmentReport;
import com.sunrisedental.model.TreatmentRevenueReport;
import com.sunrisedental.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ReportRepository {

    public List<DailyAppointmentReport>
            getDailyAppointmentReport(
                    LocalDate appointmentDate)
            throws SQLException {

        String sql = """
                SELECT
                    a.appointment_number,
                    p.patient_name,
                    d.dentist_name,
                    t.treatment_name,
                    a.appointment_time,
                    a.status
                FROM appointments a
                INNER JOIN patients p
                    ON a.patient_id = p.patient_id
                INNER JOIN dentists d
                    ON a.dentist_id = d.dentist_id
                INNER JOIN treatments t
                    ON a.treatment_id = t.treatment_id
                WHERE a.appointment_date = ?
                ORDER BY a.appointment_time
                """;

        List<DailyAppointmentReport> reports =
                new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDate(
                    1,
                    Date.valueOf(appointmentDate));

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    DailyAppointmentReport report =
                            new DailyAppointmentReport();

                    report.setAppointmentNumber(
                            resultSet.getString(
                                    "appointment_number"));

                    report.setPatientName(
                            resultSet.getString(
                                    "patient_name"));

                    report.setDentistName(
                            resultSet.getString(
                                    "dentist_name"));

                    report.setTreatmentName(
                            resultSet.getString(
                                    "treatment_name"));

                    report.setAppointmentTime(
                            resultSet.getTime(
                                    "appointment_time")
                                    .toLocalTime());

                    report.setStatus(
                            resultSet.getString(
                                    "status"));

                    reports.add(report);
                }
            }
        }

        return reports;
    }

    public List<TreatmentRevenueReport>
            getTreatmentRevenueReport(
                    LocalDate startDate,
                    LocalDate endDate)
            throws SQLException {

        String sql = """
                SELECT
                    t.treatment_name,
                    COUNT(b.bill_id) AS treatment_count,
                    COALESCE(
                        SUM(b.total_amount),
                        0
                    ) AS revenue
                FROM bills b
                INNER JOIN appointments a
                    ON b.appointment_id =
                       a.appointment_id
                INNER JOIN treatments t
                    ON a.treatment_id =
                       t.treatment_id
                WHERE DATE(b.bill_date)
                    BETWEEN ? AND ?
                GROUP BY
                    t.treatment_id,
                    t.treatment_name
                ORDER BY revenue DESC
                """;

        List<TreatmentRevenueReport> reports =
                new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDate(
                    1,
                    Date.valueOf(startDate));

            statement.setDate(
                    2,
                    Date.valueOf(endDate));

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    TreatmentRevenueReport report =
                            new TreatmentRevenueReport();

                    report.setTreatmentName(
                            resultSet.getString(
                                    "treatment_name"));

                    report.setTreatmentCount(
                            resultSet.getInt(
                                    "treatment_count"));

                    BigDecimal revenue =
                            resultSet.getBigDecimal(
                                    "revenue");

                    report.setRevenue(revenue);

                    reports.add(report);
                }
            }
        }

        return reports;
    }
}
