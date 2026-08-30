package com.sunrisedental.model;

import java.math.BigDecimal;

public class TreatmentRevenueReport {

    private String treatmentName;
    private int treatmentCount;
    private BigDecimal revenue;

    public TreatmentRevenueReport() {
    }

    public TreatmentRevenueReport(
            String treatmentName,
            int treatmentCount,
            BigDecimal revenue) {

        this.treatmentName = treatmentName;
        this.treatmentCount = treatmentCount;
        this.revenue = revenue;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(
            String treatmentName) {

        this.treatmentName =
                treatmentName;
    }

    public int getTreatmentCount() {
        return treatmentCount;
    }

    public void setTreatmentCount(
            int treatmentCount) {

        this.treatmentCount =
                treatmentCount;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(
            BigDecimal revenue) {

        this.revenue = revenue;
    }
}