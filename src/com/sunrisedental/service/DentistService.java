package com.sunrisedental.service;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.repository.DentistRepository;

import java.sql.SQLException;
import java.util.List;

public class DentistService {

    private final DentistRepository dentistRepository;

    public DentistService() {
        this.dentistRepository = new DentistRepository();
    }

    public Dentist addDentist(
            String dentistName,
            String specialization,
            String contactNumber) throws SQLException {

        validateDentistData(
                dentistName,
                specialization,
                contactNumber);

        Dentist dentist = new Dentist();

        dentist.setDentistName(dentistName.trim());
        dentist.setSpecialization(
                specialization == null
                        ? null
                        : specialization.trim());

        dentist.setContactNumber(
                contactNumber == null
                        ? null
                        : contactNumber.trim());

        dentist.setActive(true);

        return dentistRepository.create(dentist);
    }

    public Dentist getDentistById(int dentistId)
            throws SQLException {

        if (dentistId <= 0) {
            throw new IllegalArgumentException(
                    "Dentist ID must be greater than zero.");
        }

        return dentistRepository.findById(dentistId);
    }

    public List<Dentist> getAllDentists()
            throws SQLException {

        return dentistRepository.findAll();
    }

    public List<Dentist> getActiveDentists()
            throws SQLException {

        return dentistRepository.findAllActive();
    }

    public boolean updateDentist(
            int dentistId,
            String dentistName,
            String specialization,
            String contactNumber,
            boolean active) throws SQLException {

        validateDentistData(
                dentistName,
                specialization,
                contactNumber);

        Dentist dentist =
                dentistRepository.findById(dentistId);

        if (dentist == null) {
            return false;
        }

        dentist.setDentistName(dentistName.trim());

        dentist.setSpecialization(
                specialization == null
                        ? null
                        : specialization.trim());

        dentist.setContactNumber(
                contactNumber == null
                        ? null
                        : contactNumber.trim());

        dentist.setActive(active);

        return dentistRepository.update(dentist);
    }

    public boolean deleteDentist(int dentistId)
            throws SQLException {

        if (dentistId <= 0) {
            throw new IllegalArgumentException(
                    "Dentist ID must be greater than zero.");
        }

        return dentistRepository.delete(dentistId);
    }

    private void validateDentistData(
            String dentistName,
            String specialization,
            String contactNumber) {

        if (dentistName == null ||
                dentistName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Dentist name is required.");
        }

        if (contactNumber != null &&
                !contactNumber.trim().isEmpty() &&
                !contactNumber.matches("\\d{10}")) {

            throw new IllegalArgumentException(
                    "Dentist contact number must contain 10 digits.");
        }
    }
}