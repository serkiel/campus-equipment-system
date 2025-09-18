package edu.cit.melocoton.jerickiel.campusequipmentloan.controller;

import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.LoanRequest;
import edu.cit.melocoton.jerickiel.campusequipmentloan.dto.LoanReturnResponse;
import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Loan;
import edu.cit.melocoton.jerickiel.campusequipmentloan.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody @Valid LoanRequest loanRequest) {
        Loan createdLoan = loanService.createLoan(loanRequest);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanReturnResponse> returnLoan(@PathVariable Long id) {
        LoanReturnResponse response = loanService.returnLoan(id);
        return ResponseEntity.ok(response);
    }
}