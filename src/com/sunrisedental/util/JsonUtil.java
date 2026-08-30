package com.sunrisedental.util;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.model.Dentist;
import com.sunrisedental.model.Patient;
import com.sunrisedental.model.Treatment;

import java.util.List;

public class JsonUtil {

    private JsonUtil() {
    }

    public static String patientToJson(
            Patient patient) {

        if (patient == null) {
            return "null";
        }

        return "{"
                + "\"patientId\":"
                + patient.getPatientId()
                + ","
                + "\"patientName\":\""
                + HttpUtil.escapeJson(
                        patient.getPatientName())
                + "\","
                + "\"address\":\""
                + HttpUtil.escapeJson(
                        patient.getAddress())
                + "\","
                + "\"contactNumber\":\""
                + HttpUtil.escapeJson(
                        patient.getContactNumber())
                + "\""
                + "}";
    }

    public static String patientsToJson(
            List<Patient> patients) {

        StringBuilder json =
                new StringBuilder("[");

        for (int i = 0;
             i < patients.size();
             i++) {

            json.append(
                    patientToJson(
                            patients.get(i)));

            if (i < patients.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        return json.toString();
    }

    public static String dentistToJson(
            Dentist dentist) {

        if (dentist == null) {
            return "null";
        }

        return "{"
                + "\"dentistId\":"
                + dentist.getDentistId()
                + ","
                + "\"dentistName\":\""
                + HttpUtil.escapeJson(
                        dentist.getDentistName())
                + "\","
                + "\"specialization\":\""
                + HttpUtil.escapeJson(
                        dentist.getSpecialization())
                + "\","
                + "\"contactNumber\":\""
                + HttpUtil.escapeJson(
                        dentist.getContactNumber())
                + "\","
                + "\"active\":"
                + dentist.isActive()
                + "}";
    }

    public static String dentistsToJson(
            List<Dentist> dentists) {

        StringBuilder json =
                new StringBuilder("[");

        for (int i = 0;
             i < dentists.size();
             i++) {

            json.append(
                    dentistToJson(
                            dentists.get(i)));

            if (i < dentists.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        return json.toString();
    }

    public static String treatmentToJson(
            Treatment treatment) {

        if (treatment == null) {
            return "null";
        }

        return "{"
                + "\"treatmentId\":"
                + treatment.getTreatmentId()
                + ","
                + "\"treatmentName\":\""
                + HttpUtil.escapeJson(
                        treatment.getTreatmentName())
                + "\","
                + "\"treatmentCost\":"
                + treatment.getTreatmentCost()
                + ","
                + "\"active\":"
                + treatment.isActive()
                + "}";
    }

    public static String treatmentsToJson(
            List<Treatment> treatments) {

        StringBuilder json =
                new StringBuilder("[");

        for (int i = 0;
             i < treatments.size();
             i++) {

            json.append(
                    treatmentToJson(
                            treatments.get(i)));

            if (i < treatments.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        return json.toString();
    }

    public static String appointmentToJson(
            Appointment appointment) {

        if (appointment == null) {
            return "null";
        }

        return "{"
                + "\"appointmentId\":"
                + appointment.getAppointmentId()
                + ","
                + "\"appointmentNumber\":\""
                + HttpUtil.escapeJson(
                        appointment
                                .getAppointmentNumber())
                + "\","
                + "\"patientId\":"
                + appointment.getPatientId()
                + ","
                + "\"dentistId\":"
                + appointment.getDentistId()
                + ","
                + "\"treatmentId\":"
                + appointment.getTreatmentId()
                + ","
                + "\"appointmentDate\":\""
                + appointment
                        .getAppointmentDate()
                + "\","
                + "\"appointmentTime\":\""
                + appointment
                        .getAppointmentTime()
                + "\","
                + "\"status\":\""
                + HttpUtil.escapeJson(
                        appointment.getStatus())
                + "\""
                + "}";
    }

    public static String appointmentsToJson(
            List<Appointment> appointments) {

        StringBuilder json =
                new StringBuilder("[");

        for (int i = 0;
             i < appointments.size();
             i++) {

            json.append(
                    appointmentToJson(
                            appointments.get(i)));

            if (i < appointments.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        return json.toString();
    }

    public static String billToJson(
            Bill bill) {

        if (bill == null) {
            return "null";
        }

        String billDate = "";

        if (bill.getBillDate() != null) {

            billDate =
                    bill.getBillDate()
                            .toString();
        }

        return "{"
                + "\"billId\":"
                + bill.getBillId()
                + ","
                + "\"appointmentId\":"
                + bill.getAppointmentId()
                + ","
                + "\"consultationFee\":"
                + bill.getConsultationFee()
                + ","
                + "\"treatmentCost\":"
                + bill.getTreatmentCost()
                + ","
                + "\"totalAmount\":"
                + bill.getTotalAmount()
                + ","
                + "\"billDate\":\""
                + billDate
                + "\""
                + "}";
    }

    public static String billsToJson(
            List<Bill> bills) {

        StringBuilder json =
                new StringBuilder("[");

        for (int i = 0;
             i < bills.size();
             i++) {

            json.append(
                    billToJson(
                            bills.get(i)));

            if (i < bills.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        return json.toString();
    }
}