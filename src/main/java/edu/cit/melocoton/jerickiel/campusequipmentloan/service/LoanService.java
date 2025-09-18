package edu.cit.melocoton.jerickiel.campusequipmentloan.service;

import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.LoanRequest;
import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.LoanReturnResponse;
import edu.cit.melocoton.jerickiel.campusequipmentloan.model.*;
import edu.cit.melocoton.jerickiel.campusequipmentloan.repository.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final InMemoryRepository inMemoryRepository;
    private final PenaltyCalculationStrategy penaltyCalculationStrategy;

    @Autowired
    public LoanService(InMemoryRepository inMemoryRepository, PenaltyCalculationStrategy penaltyCalculationStrategy) {
        this.inMemoryRepository = inMemoryRepository;
        this.penaltyCalculationStrategy = penaltyCalculationStrategy;
    }

    public Loan createLoan(LoanRequest loanRequest) {
        Student student = inMemoryRepository.findStudentById(loanRequest.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found."));
        Equipment equipment = inMemoryRepository.findEquipmentById(loanRequest.getEquipmentId())
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found."));

        if (!equipment.isAvailability()) {
            throw new IllegalStateException("Equipment is not available.");
        }

        List<Loan> activeLoans = inMemoryRepository.findActiveLoansByStudentId(student.getId());
        if (activeLoans.size() >= 2) {
            throw new IllegalStateException("Student has reached the maximum limit of 2 active loans.");
        }

        Loan newLoan = new Loan();
        newLoan.setStudent(student);
        newLoan.setEquipment(equipment);
        newLoan.setStartDate(LocalDate.now());
        newLoan.setDueDate(LocalDate.now().plusDays(7));
        newLoan.setStatus(LoanStatus.ACTIVE);

        equipment.setAvailability(false);
        inMemoryRepository.save(equipment);
        return inMemoryRepository.save(newLoan);
    }

    public LoanReturnResponse returnLoan(Long loanId) {
        Loan loan = inMemoryRepository.findLoanById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found."));

        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new IllegalStateException("Loan is not active and cannot be returned.");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(loan.getReturnDate().isAfter(loan.getDueDate()) ? LoanStatus.OVERDUE : LoanStatus.RETURNED);

        double penalty = penaltyCalculationStrategy.calculatePenalty(loan);
        loan.getEquipment().setAvailability(true);
        inMemoryRepository.save(loan.getEquipment());
        Loan savedLoan = inMemoryRepository.save(loan);

        return new LoanReturnResponse(savedLoan, penalty);
    }

    public List<Equipment> getAvailableEquipment() {
        return inMemoryRepository.findAllAvailableEquipment();
    }
}