package com.sunrisedental.service;

import com.sunrisedental.model.Patient;
import com.sunrisedental.repository.PatientRepository;

import java.sql.SQLException;
import java.util.List;

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService() {
        this.patientRepository = new PatientRepository();
    }

    public Patient registerPatient(
            String patientName,
            String address,
            String contactNumber) throws SQLException {

        validatePatientData(
                patientName,
                address,
                contactNumber);

        Patient patient = new Patient();

        patient.setPatientName(patientName.trim());
        patient.setAddress(address.trim());
        patient.setContactNumber(contactNumber.trim());

        return patientRepository.create(patient);
    }

    public Patient getPatientById(int patientId)
            throws SQLException {

        if (patientId <= 0) {
            throw new IllegalArgumentException(
                    "Patient ID must be greater than zero.");
        }

        return patientRepository.findById(patientId);
    }

    public List<Patient> getAllPatients()
            throws SQLException {

        return patientRepository.findAll();
    }

    public boolean updatePatient(
            int patientId,
            String patientName,
            String address,
            String contactNumber) throws SQLException {

        validatePatientData(
                patientName,
                address,
                contactNumber);

        Patient patient =
                patientRepository.findById(patientId);

        if (patient == null) {
            return false;
        }

        patient.setPatientName(patientName.trim());
        patient.setAddress(address.trim());
        patient.setContactNumber(contactNumber.trim());

        return patientRepository.update(patient);
    }

    public boolean deletePatient(int patientId)
            throws SQLException {

        if (patientId <= 0) {
            throw new IllegalArgumentException(
                    "Patient ID must be greater than zero.");
        }

        return patientRepository.delete(patientId);
    }

    private void validatePatientData(
            String patientName,
            String address,
            String contactNumber) {

        if (patientName == null ||
                patientName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Patient name is required.");
        }

        if (address == null ||
                address.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Patient address is required.");
        }

        if (contactNumber == null ||
                contactNumber.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Contact number is required.");
        }

        if (!contactNumber.matches("\\d{10}")) {

            throw new IllegalArgumentException(
                    "Contact number must contain exactly 10 digits.");
        }
    }
}