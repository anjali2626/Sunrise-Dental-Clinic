package com.sunrisedental.repository;

import com.sunrisedental.dto.TodaysAppointmentDTO;
import com.sunrisedental.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {

    public int getTotalPatients() throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM patients";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    public int getTodaysAppointments() throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                "FROM appointments " +
                "WHERE appointment_date = CURDATE()";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    public int getActiveDentists() throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                "FROM dentists " +
                "WHERE active = TRUE";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    public double getTodaysRevenue() throws SQLException {

        String sql =
                "SELECT COALESCE(SUM(total_amount), 0) " +
                "FROM bills " +
                "WHERE DATE(bill_date) = CURDATE()";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        }

        return 0.0;
    }

    public List<TodaysAppointmentDTO> getTodaysAppointmentList()
            throws SQLException {

        List<TodaysAppointmentDTO> appointments =
                new ArrayList<>();

        String sql =
                "SELECT a.appointment_number, " +
                "p.patient_name, " +
                "d.dentist_name, " +
                "TIME_FORMAT(a.appointment_time, '%h:%i %p') AS appointment_time, " +
                "t.treatment_name, " +
                "a.status " +
                "FROM appointments a " +
                "INNER JOIN patients p " +
                "ON a.patient_id = p.patient_id " +
                "INNER JOIN dentists d " +
                "ON a.dentist_id = d.dentist_id " +
                "INNER JOIN treatments t " +
                "ON a.treatment_id = t.treatment_id " +
                "WHERE a.appointment_date = CURDATE() " +
                "ORDER BY a.appointment_time ASC";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                TodaysAppointmentDTO appointment =
                        new TodaysAppointmentDTO();

                appointment.setAppointmentNumber(
                        resultSet.getString(
                                "appointment_number"));

                appointment.setPatientName(
                        resultSet.getString(
                                "patient_name"));

                appointment.setDentistName(
                        resultSet.getString(
                                "dentist_name"));

                appointment.setAppointmentTime(
                        resultSet.getString(
                                "appointment_time"));

                appointment.setTreatmentName(
                        resultSet.getString(
                                "treatment_name"));

                appointment.setStatus(
                        resultSet.getString(
                                "status"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }
}