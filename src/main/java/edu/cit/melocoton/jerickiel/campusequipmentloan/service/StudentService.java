package edu.cit.melocoton.jerickiel.campusequipmentloan.service;

import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.StudentLoginRequest;
import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.StudentRegistrationRequest;
import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Student;
import edu.cit.melocoton.jerickiel.campusequipmentloan.repository.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final InMemoryRepository inMemoryRepository;

    @Autowired
    public StudentService(InMemoryRepository inMemoryRepository) {
        this.inMemoryRepository = inMemoryRepository;
    }

    public Student registerStudent(StudentRegistrationRequest request) {
        if (inMemoryRepository.isStudentExist(request.getStudentNo(), request.getEmail())) {
            throw new IllegalArgumentException("A student with this Student No or Email already exists.");
        }

        Student newStudent = new Student();
        newStudent.setStudentNo(request.getStudentNo());
        newStudent.setName(request.getName());
        newStudent.setEmail(request.getEmail());

        return inMemoryRepository.save(newStudent);
    }

    public Student loginStudent(StudentLoginRequest request) {
        return inMemoryRepository.findStudentByStudentNoAndEmail(request.getStudentNo(), request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Student No or Email."));
    }
}