package edu.cit.melocoton.jerickiel.campusequipmentloan.dto;

import jakarta.validation.constraints.NotNull;

public class LoanRequest {
    @NotNull
    private Long studentId;
    @NotNull
    private Long equipmentId;
    // Getters and Setters

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }
}