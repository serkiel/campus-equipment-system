package edu.cit.melocoton.jerickiel.campusequipmentloan.dto;

import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Loan;

public class LoanReturnResponse {
    private Loan loan;
    private double penalty;
    // Constructor, Getters and Setters

    public LoanReturnResponse(Loan loan, double penalty) {
        this.loan = loan;
        this.penalty = penalty;
    }

    public LoanReturnResponse() {
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }
}
