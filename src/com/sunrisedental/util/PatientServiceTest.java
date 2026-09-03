package com.sunrisedental.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sunrisedental.model.Patient;
import com.sunrisedental.service.PatientService;

public class PatientServiceTest {

    @Test
    public void testGetAllPatients() throws SQLException {

        PatientService service = new PatientService();

        List<Patient> patients = service.getAllPatients();

        assertNotNull(patients, "Patient list should not be null");
    }
}