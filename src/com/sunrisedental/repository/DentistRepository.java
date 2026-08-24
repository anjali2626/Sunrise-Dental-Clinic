package com.sunrisedental.repository;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DentistRepository {

    public Dentist create(Dentist dentist) throws SQLException {

        String sql = """
                INSERT INTO dentists
                (dentist_name, specialization, contact_number, active)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, dentist.getDentistName());
            statement.setString(2, dentist.getSpecialization());
            statement.setString(3, dentist.getContactNumber());
            statement.setBoolean(4, dentist.isActive());

            statement.executeUpdate();

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    dentist.setDentistId(generatedKeys.getInt(1));
                }
            }
        }

        return dentist;
    }

    public Dentist findById(int dentistId) throws SQLException {

        String sql = """
                SELECT dentist_id, dentist_name, specialization,
                       contact_number, active
                FROM dentists
                WHERE dentist_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, dentistId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToDentist(resultSet);
                }
            }
        }

        return null;
    }

    public List<Dentist> findAll() throws SQLException {

        String sql = """
                SELECT dentist_id, dentist_name, specialization,
                       contact_number, active
                FROM dentists
                ORDER BY dentist_id
                """;

        List<Dentist> dentists = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                dentists.add(mapResultSetToDentist(resultSet));
            }
        }

        return dentists;
    }

    public List<Dentist> findAllActive() throws SQLException {

        String sql = """
                SELECT dentist_id, dentist_name, specialization,
                       contact_number, active
                FROM dentists
                WHERE active = TRUE
                ORDER BY dentist_name
                """;

        List<Dentist> dentists = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                dentists.add(mapResultSetToDentist(resultSet));
            }
        }

        return dentists;
    }

    public boolean update(Dentist dentist) throws SQLException {

        String sql = """
                UPDATE dentists
                SET dentist_name = ?,
                    specialization = ?,
                    contact_number = ?,
                    active = ?
                WHERE dentist_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, dentist.getDentistName());
            statement.setString(2, dentist.getSpecialization());
            statement.setString(3, dentist.getContactNumber());
            statement.setBoolean(4, dentist.isActive());
            statement.setInt(5, dentist.getDentistId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int dentistId) throws SQLException {

        String sql = """
                DELETE FROM dentists
                WHERE dentist_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, dentistId);

            return statement.executeUpdate() > 0;
        }
    }

    private Dentist mapResultSetToDentist(ResultSet resultSet)
            throws SQLException {

        Dentist dentist = new Dentist();

        dentist.setDentistId(
                resultSet.getInt("dentist_id"));

        dentist.setDentistName(
                resultSet.getString("dentist_name"));

        dentist.setSpecialization(
                resultSet.getString("specialization"));

        dentist.setContactNumber(
                resultSet.getString("contact_number"));

        dentist.setActive(
                resultSet.getBoolean("active"));

        return dentist;
    }
}