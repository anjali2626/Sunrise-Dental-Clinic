package com.sunrisedental.repository;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentRepository {

    public Treatment create(Treatment treatment) throws SQLException {

        String sql = """
                INSERT INTO treatments
                (treatment_name, treatment_cost, active)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, treatment.getTreatmentName());
            statement.setBigDecimal(2, treatment.getTreatmentCost());
            statement.setBoolean(3, treatment.isActive());

            statement.executeUpdate();

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    treatment.setTreatmentId(
                            generatedKeys.getInt(1));
                }
            }
        }

        return treatment;
    }

    public Treatment findById(int treatmentId) throws SQLException {

        String sql = """
                SELECT treatment_id, treatment_name,
                       treatment_cost, active
                FROM treatments
                WHERE treatment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, treatmentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToTreatment(resultSet);
                }
            }
        }

        return null;
    }

    public List<Treatment> findAll() throws SQLException {

        String sql = """
                SELECT treatment_id, treatment_name,
                       treatment_cost, active
                FROM treatments
                ORDER BY treatment_id
                """;

        List<Treatment> treatments = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                treatments.add(mapResultSetToTreatment(resultSet));
            }
        }

        return treatments;
    }

    public List<Treatment> findAllActive() throws SQLException {

        String sql = """
                SELECT treatment_id, treatment_name,
                       treatment_cost, active
                FROM treatments
                WHERE active = TRUE
                ORDER BY treatment_name
                """;

        List<Treatment> treatments = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                treatments.add(mapResultSetToTreatment(resultSet));
            }
        }

        return treatments;
    }

    public boolean update(Treatment treatment) throws SQLException {

        String sql = """
                UPDATE treatments
                SET treatment_name = ?,
                    treatment_cost = ?,
                    active = ?
                WHERE treatment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, treatment.getTreatmentName());
            statement.setBigDecimal(2, treatment.getTreatmentCost());
            statement.setBoolean(3, treatment.isActive());
            statement.setInt(4, treatment.getTreatmentId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int treatmentId) throws SQLException {

        String sql = """
                DELETE FROM treatments
                WHERE treatment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, treatmentId);

            return statement.executeUpdate() > 0;
        }
    }

    private Treatment mapResultSetToTreatment(ResultSet resultSet)
            throws SQLException {

        Treatment treatment = new Treatment();

        treatment.setTreatmentId(
                resultSet.getInt("treatment_id"));

        treatment.setTreatmentName(
                resultSet.getString("treatment_name"));

        treatment.setTreatmentCost(
                resultSet.getBigDecimal("treatment_cost"));

        treatment.setActive(
                resultSet.getBoolean("active"));

        return treatment;
    }
}