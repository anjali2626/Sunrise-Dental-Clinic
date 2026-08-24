package com.sunrisedental.repository;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    public Appointment create(Appointment appointment)
            throws SQLException {

        String sql = """
                INSERT INTO appointments
                (appointment_number, patient_id, dentist_id,
                 treatment_id, appointment_date, appointment_time, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(
                    1, appointment.getAppointmentNumber());

            statement.setInt(
                    2, appointment.getPatientId());

            statement.setInt(
                    3, appointment.getDentistId());

            statement.setInt(
                    4, appointment.getTreatmentId());

            statement.setDate(
                    5, Date.valueOf(appointment.getAppointmentDate()));

            statement.setTime(
                    6, Time.valueOf(appointment.getAppointmentTime()));

            statement.setString(
                    7, appointment.getStatus());

            statement.executeUpdate();

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    appointment.setAppointmentId(
                            generatedKeys.getInt(1));
                }
            }
        }

        return appointment;
    }

    public Appointment findById(int appointmentId)
            throws SQLException {

        String sql = """
                SELECT appointment_id, appointment_number,
                       patient_id, dentist_id, treatment_id,
                       appointment_date, appointment_time,
                       status, created_at
                FROM appointments
                WHERE appointment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToAppointment(resultSet);
                }
            }
        }

        return null;
    }

    public Appointment findByAppointmentNumber(
            String appointmentNumber) throws SQLException {

        String sql = """
                SELECT appointment_id, appointment_number,
                       patient_id, dentist_id, treatment_id,
                       appointment_date, appointment_time,
                       status, created_at
                FROM appointments
                WHERE appointment_number = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, appointmentNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToAppointment(resultSet);
                }
            }
        }

        return null;
    }

    public List<Appointment> findAll() throws SQLException {

        String sql = """
                SELECT appointment_id, appointment_number,
                       patient_id, dentist_id, treatment_id,
                       appointment_date, appointment_time,
                       status, created_at
                FROM appointments
                ORDER BY appointment_date, appointment_time
                """;

        List<Appointment> appointments = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                appointments.add(
                        mapResultSetToAppointment(resultSet));
            }
        }

        return appointments;
    }

    public List<Appointment> findByDate(
            java.time.LocalDate date) throws SQLException {

        String sql = """
                SELECT appointment_id, appointment_number,
                       patient_id, dentist_id, treatment_id,
                       appointment_date, appointment_time,
                       status, created_at
                FROM appointments
                WHERE appointment_date = ?
                ORDER BY appointment_time
                """;

        List<Appointment> appointments = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(date));

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    appointments.add(
                            mapResultSetToAppointment(resultSet));
                }
            }
        }

        return appointments;
    }

    public boolean isDentistAvailable(
            int dentistId,
            java.time.LocalDate appointmentDate,
            java.time.LocalTime appointmentTime)
            throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM appointments
                WHERE dentist_id = ?
                  AND appointment_date = ?
                  AND appointment_time = ?
                  AND status <> 'CANCELLED'
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, dentistId);
            statement.setDate(
                    2, Date.valueOf(appointmentDate));
            statement.setTime(
                    3, Time.valueOf(appointmentTime));

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0;
                }
            }
        }

        return false;
    }

    public boolean update(Appointment appointment)
            throws SQLException {

        String sql = """
                UPDATE appointments
                SET appointment_number = ?,
                    patient_id = ?,
                    dentist_id = ?,
                    treatment_id = ?,
                    appointment_date = ?,
                    appointment_time = ?,
                    status = ?
                WHERE appointment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1, appointment.getAppointmentNumber());

            statement.setInt(
                    2, appointment.getPatientId());

            statement.setInt(
                    3, appointment.getDentistId());

            statement.setInt(
                    4, appointment.getTreatmentId());

            statement.setDate(
                    5, Date.valueOf(
                            appointment.getAppointmentDate()));

            statement.setTime(
                    6, Time.valueOf(
                            appointment.getAppointmentTime()));

            statement.setString(
                    7, appointment.getStatus());

            statement.setInt(
                    8, appointment.getAppointmentId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(
            int appointmentId,
            String status) throws SQLException {

        String sql = """
                UPDATE appointments
                SET status = ?
                WHERE appointment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, appointmentId);

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int appointmentId)
            throws SQLException {

        String sql = """
                DELETE FROM appointments
                WHERE appointment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);

            return statement.executeUpdate() > 0;
        }
    }

    private Appointment mapResultSetToAppointment(
            ResultSet resultSet) throws SQLException {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(
                resultSet.getInt("appointment_id"));

        appointment.setAppointmentNumber(
                resultSet.getString("appointment_number"));

        appointment.setPatientId(
                resultSet.getInt("patient_id"));

        appointment.setDentistId(
                resultSet.getInt("dentist_id"));

        appointment.setTreatmentId(
                resultSet.getInt("treatment_id"));

        appointment.setAppointmentDate(
                resultSet.getDate("appointment_date")
                        .toLocalDate());

        appointment.setAppointmentTime(
                resultSet.getTime("appointment_time")
                        .toLocalTime());

        appointment.setStatus(
                resultSet.getString("status"));

        if (resultSet.getTimestamp("created_at") != null) {

            appointment.setCreatedAt(
                    resultSet.getTimestamp("created_at")
                            .toLocalDateTime());
        }

        return appointment;
    }
}