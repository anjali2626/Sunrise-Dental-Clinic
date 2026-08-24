package com.sunrisedental.service;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.repository.TreatmentRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TreatmentService {

    private final TreatmentRepository treatmentRepository;

    public TreatmentService() {
        this.treatmentRepository = new TreatmentRepository();
    }

    public Treatment addTreatment(
            String treatmentName,
            BigDecimal treatmentCost) throws SQLException {

        validateTreatmentData(
                treatmentName,
                treatmentCost);

        Treatment treatment = new Treatment();

        treatment.setTreatmentName(
                treatmentName.trim());

        treatment.setTreatmentCost(
                treatmentCost);

        treatment.setActive(true);

        return treatmentRepository.create(treatment);
    }

    public Treatment getTreatmentById(
            int treatmentId) throws SQLException {

        if (treatmentId <= 0) {
            throw new IllegalArgumentException(
                    "Treatment ID must be greater than zero.");
        }

        return treatmentRepository.findById(treatmentId);
    }

    public List<Treatment> getAllTreatments()
            throws SQLException {

        return treatmentRepository.findAll();
    }

    public List<Treatment> getActiveTreatments()
            throws SQLException {

        return treatmentRepository.findAllActive();
    }

    public boolean updateTreatment(
            int treatmentId,
            String treatmentName,
            BigDecimal treatmentCost,
            boolean active) throws SQLException {

        validateTreatmentData(
                treatmentName,
                treatmentCost);

        Treatment treatment =
                treatmentRepository.findById(treatmentId);

        if (treatment == null) {
            return false;
        }

        treatment.setTreatmentName(
                treatmentName.trim());

        treatment.setTreatmentCost(
                treatmentCost);

        treatment.setActive(active);

        return treatmentRepository.update(treatment);
    }

    public boolean deleteTreatment(int treatmentId)
            throws SQLException {

        if (treatmentId <= 0) {
            throw new IllegalArgumentException(
                    "Treatment ID must be greater than zero.");
        }

        return treatmentRepository.delete(treatmentId);
    }

    private void validateTreatmentData(
            String treatmentName,
            BigDecimal treatmentCost) {

        if (treatmentName == null ||
                treatmentName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Treatment name is required.");
        }

        if (treatmentCost == null ||
                treatmentCost.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Treatment cost must be greater than zero.");
        }
    }
}