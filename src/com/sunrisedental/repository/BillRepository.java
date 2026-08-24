package com.sunrisedental.repository;

import com.sunrisedental.model.Bill;
import com.sunrisedental.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillRepository {

    public Bill create(Bill bill) throws SQLException {

        String sql = """
                INSERT INTO bills
                (appointment_id, consultation_fee,
                 treatment_cost, total_amount)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(
                    1, bill.getAppointmentId());

            statement.setBigDecimal(
                    2, bill.getConsultationFee());

            statement.setBigDecimal(
                    3, bill.getTreatmentCost());

            statement.setBigDecimal(
                    4, bill.getTotalAmount());

            statement.executeUpdate();

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {

                    bill.setBillId(
                            generatedKeys.getInt(1));
                }
            }
        }

        return bill;
    }

    public Bill findById(int billId)
            throws SQLException {

        String sql = """
                SELECT bill_id, appointment_id,
                       consultation_fee, treatment_cost,
                       total_amount, bill_date
                FROM bills
                WHERE bill_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, billId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToBill(resultSet);
                }
            }
        }

        return null;
    }

    public Bill findByAppointmentId(
            int appointmentId) throws SQLException {

        String sql = """
                SELECT bill_id, appointment_id,
                       consultation_fee, treatment_cost,
                       total_amount, bill_date
                FROM bills
                WHERE appointment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToBill(resultSet);
                }
            }
        }

        return null;
    }

    public boolean update(Bill bill)
            throws SQLException {

        String sql = """
                UPDATE bills
                SET consultation_fee = ?,
                    treatment_cost = ?,
                    total_amount = ?
                WHERE bill_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setBigDecimal(
                    1, bill.getConsultationFee());

            statement.setBigDecimal(
                    2, bill.getTreatmentCost());

            statement.setBigDecimal(
                    3, bill.getTotalAmount());

            statement.setInt(
                    4, bill.getBillId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int billId)
            throws SQLException {

        String sql = """
                DELETE FROM bills
                WHERE bill_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, billId);

            return statement.executeUpdate() > 0;
        }
    }

    private Bill mapResultSetToBill(
            ResultSet resultSet) throws SQLException {

        Bill bill = new Bill();

        bill.setBillId(
                resultSet.getInt("bill_id"));

        bill.setAppointmentId(
                resultSet.getInt("appointment_id"));

        bill.setConsultationFee(
                resultSet.getBigDecimal(
                        "consultation_fee"));

        bill.setTreatmentCost(
                resultSet.getBigDecimal(
                        "treatment_cost"));

        bill.setTotalAmount(
                resultSet.getBigDecimal(
                        "total_amount"));

        if (resultSet.getTimestamp("bill_date") != null) {

            bill.setBillDate(
                    resultSet.getTimestamp("bill_date")
                            .toLocalDateTime());
        }

        return bill;
    }
}