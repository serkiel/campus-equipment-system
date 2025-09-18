package edu.cit.melocoton.jerickiel.campusequipmentloan.service;

import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Loan;

public interface PenaltyCalculationStrategy {
    double calculatePenalty(Loan loan);
}