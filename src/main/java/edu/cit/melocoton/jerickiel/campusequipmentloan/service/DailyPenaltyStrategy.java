package edu.cit.melocoton.jerickiel.campusequipmentloan.service;

import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Loan;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

@Service
public class DailyPenaltyStrategy implements PenaltyCalculationStrategy {
    @Override
    public double calculatePenalty(Loan loan) {
        if (loan.getReturnDate() == null || !loan.getReturnDate().isAfter(loan.getDueDate())) {
            return 0.0;
        }
        long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        return overdueDays * 50.0;
    }
}