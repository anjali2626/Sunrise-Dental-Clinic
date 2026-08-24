package com.sunrisedental.repository;

import com.sunrisedental.model.Patient;
import com.sunrisedental.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {

    public Patient create(Patient patient) throws SQLException {

        String sql = """
                INSERT INTO patients
                (patient_name, address, contact_number)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, patient.getPatientName());
            statement.setString(2, patient.getAddress());
            statement.setString(3, patient.getContactNumber());

            statement.executeUpdate();

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    patient.setPatientId(
                            generatedKeys.getInt(1));
                }
            }
        }

        return patient;
    }

    public Patient findById(int patientId) throws SQLException {

        String sql = """
                SELECT patient_id, patient_name, address,
                       contact_number, created_at
                FROM patients
                WHERE patient_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToPatient(resultSet);
                }
            }
        }

        return null;
    }

    public List<Patient> findAll() throws SQLException {

        String sql = """
                SELECT patient_id, patient_name, address,
                       contact_number, created_at
                FROM patients
                ORDER BY patient_id
                """;

        List<Patient> patients = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                patients.add(mapResultSetToPatient(resultSet));
            }
        }

        return patients;
    }

    public boolean update(Patient patient) throws SQLException {

        String sql = """
                UPDATE patients
                SET patient_name = ?,
                    address = ?,
                    contact_number = ?
                WHERE patient_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, patient.getPatientName());
            statement.setString(2, patient.getAddress());
            statement.setString(3, patient.getContactNumber());
            statement.setInt(4, patient.getPatientId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int patientId) throws SQLException {

        String sql = """
                DELETE FROM patients
                WHERE patient_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);

            return statement.executeUpdate() > 0;
        }
    }

    private Patient mapResultSetToPatient(ResultSet resultSet)
            throws SQLException {

        Patient patient = new Patient();

        patient.setPatientId(
                resultSet.getInt("patient_id"));

        patient.setPatientName(
                resultSet.getString("patient_name"));

        patient.setAddress(
                resultSet.getString("address"));

        patient.setContactNumber(
                resultSet.getString("contact_number"));

        if (resultSet.getTimestamp("created_at") != null) {
            patient.setCreatedAt(
                    resultSet.getTimestamp("created_at")
                            .toLocalDateTime());
        }

        return patient;
    }
}