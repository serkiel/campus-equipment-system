package edu.cit.melocoton.jerickiel.campusequipmentloan.controller;

import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Equipment;
import edu.cit.melocoton.jerickiel.campusequipmentloan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    private final LoanService loanService;

    @Autowired
    public EquipmentController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/available")
    public ResponseEntity<List<Equipment>> getAvailableEquipment() {
        List<Equipment> availableEquipment = loanService.getAvailableEquipment();
        return ResponseEntity.ok(availableEquipment);
    }
}