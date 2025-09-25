package edu.cit.melocoton.jerickiel.campusequipmentloan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StudentLoginRequest {

    @NotBlank(message = "Student Number is required")
    private String studentNo;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}