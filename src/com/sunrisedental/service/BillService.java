package com.sunrisedental.service;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.model.Treatment;
import com.sunrisedental.repository.AppointmentRepository;
import com.sunrisedental.repository.BillRepository;
import com.sunrisedental.repository.TreatmentRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BillService {

    private final BillRepository billRepository;

    private final AppointmentRepository
            appointmentRepository;

    private final TreatmentRepository
            treatmentRepository;

    public BillService() {

        this.billRepository =
                new BillRepository();

        this.appointmentRepository =
                new AppointmentRepository();

        this.treatmentRepository =
                new TreatmentRepository();
    }

    public Bill generateBill(
            int appointmentId,
            BigDecimal consultationFee)
            throws SQLException {

        if (appointmentId <= 0) {

            throw new IllegalArgumentException(
                    "Appointment ID must be greater than zero.");
        }

        if (consultationFee == null ||
                consultationFee.compareTo(
                        BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Consultation fee cannot be negative.");
        }

        Appointment appointment =
                appointmentRepository.findById(
                        appointmentId);

        if (appointment == null) {

            throw new IllegalArgumentException(
                    "Appointment not found.");
        }

        Bill existingBill =
                billRepository.findByAppointmentId(
                        appointmentId);

        if (existingBill != null) {

            throw new IllegalArgumentException(
                    "A bill already exists for this appointment.");
        }

        Treatment treatment =
                treatmentRepository.findById(
                        appointment.getTreatmentId());

        if (treatment == null) {

            throw new IllegalArgumentException(
                    "Treatment not found.");
        }

        BigDecimal treatmentCost =
                treatment.getTreatmentCost();

        BigDecimal totalAmount =
                consultationFee.add(
                        treatmentCost);

        Bill bill = new Bill();

        bill.setAppointmentId(
                appointmentId);

        bill.setConsultationFee(
                consultationFee);

        bill.setTreatmentCost(
                treatmentCost);

        bill.setTotalAmount(
                totalAmount);

        return billRepository.create(
                bill);
    }

    public Bill getBillById(
            int billId)
            throws SQLException {

        if (billId <= 0) {

            throw new IllegalArgumentException(
                    "Bill ID must be greater than zero.");
        }

        return billRepository.findById(
                billId);
    }

    public List<Bill> getAllBills()
            throws SQLException {

        return billRepository.findAll();
    }

    public Bill getBillByAppointmentId(
            int appointmentId)
            throws SQLException {

        if (appointmentId <= 0) {

            throw new IllegalArgumentException(
                    "Appointment ID must be greater than zero.");
        }

        return billRepository
                .findByAppointmentId(
                        appointmentId);
    }

    public boolean updateBill(
            int billId,
            BigDecimal consultationFee)
            throws SQLException {

        if (consultationFee == null ||
                consultationFee.compareTo(
                        BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Consultation fee cannot be negative.");
        }

        Bill bill =
                billRepository.findById(
                        billId);

        if (bill == null) {

            return false;
        }

        Appointment appointment =
                appointmentRepository.findById(
                        bill.getAppointmentId());

        if (appointment == null) {

            return false;
        }

        Treatment treatment =
                treatmentRepository.findById(
                        appointment.getTreatmentId());

        if (treatment == null) {

            throw new IllegalArgumentException(
                    "Treatment not found.");
        }

        BigDecimal totalAmount =
                consultationFee.add(
                        treatment.getTreatmentCost());

        bill.setConsultationFee(
                consultationFee);

        bill.setTreatmentCost(
                treatment.getTreatmentCost());

        bill.setTotalAmount(
                totalAmount);

        return billRepository.update(
                bill);
    }

    public boolean deleteBill(
            int billId)
            throws SQLException {

        if (billId <= 0) {

            throw new IllegalArgumentException(
                    "Bill ID must be greater than zero.");
        }

        return billRepository.delete(
                billId);
    }
}