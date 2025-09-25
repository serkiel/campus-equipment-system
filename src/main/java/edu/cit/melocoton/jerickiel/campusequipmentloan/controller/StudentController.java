package edu.cit.melocoton.jerickiel.campusequipmentloan.controller;

import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.StudentLoginRequest;
import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.StudentRegistrationRequest;
import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Student;
import edu.cit.melocoton.jerickiel.campusequipmentloan.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody @Valid StudentRegistrationRequest request) {
        Student newStudent = studentService.registerStudent(request);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Student> loginStudent(@RequestBody @Valid StudentLoginRequest request) {
        Student student = studentService.loginStudent(request);
        return ResponseEntity.ok(student);
    }
}