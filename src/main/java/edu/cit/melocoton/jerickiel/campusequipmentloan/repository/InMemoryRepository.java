package edu.cit.melocoton.jerickiel.campusequipmentloan.repository;

import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Equipment;
import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Loan;
import edu.cit.melocoton.jerickiel.campusequipmentloan.model.LoanStatus;
import edu.cit.melocoton.jerickiel.campusequipmentloan.model.Student;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryRepository {

    private final Map<Long, Student> students = new HashMap<>();
    private final Map<Long, Equipment> equipment = new HashMap<>();
    private final Map<Long, Loan> loans = new HashMap<>();

    private final AtomicLong studentIdCounter = new AtomicLong(0);
    private final AtomicLong equipmentIdCounter = new AtomicLong(0);
    private final AtomicLong loanIdCounter = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        Student student1 = new Student();
        student1.setId(studentIdCounter.incrementAndGet());
        student1.setStudentNo("S001");
        student1.setName("John Doe");
        student1.setEmail("john.doe@email.com");
        students.put(student1.getId(), student1);

        Equipment equipment1 = new Equipment();
        equipment1.setId(equipmentIdCounter.incrementAndGet());
        equipment1.setName("Projector");
        equipment1.setType("Electronics");
        equipment1.setSerialNumber("SN-P-001");
        equipment1.setAvailability(true);
        equipment.put(equipment1.getId(), equipment1);

        Equipment equipment2 = new Equipment();
        equipment2.setId(equipmentIdCounter.incrementAndGet());
        equipment2.setName("Laptop");
        equipment2.setType("Computer");
        equipment2.setSerialNumber("SN-L-002");
        equipment2.setAvailability(true);
        equipment.put(equipment2.getId(), equipment2);

        Equipment equipment3 = new Equipment();
        equipment3.setId(equipmentIdCounter.incrementAndGet());
        equipment3.setName("Microphone");
        equipment3.setType("Audio");
        equipment3.setSerialNumber("SN-M-003");
        equipment3.setAvailability(false);
        equipment.put(equipment3.getId(), equipment3);
    }

    public <T> T save(T entity) {
        if (entity instanceof Student) {
            Student student = (Student) entity;
            if (student.getId() == null) {
                student.setId(studentIdCounter.incrementAndGet());
            }
            students.put(student.getId(), student);
            return entity;
        } else if (entity instanceof Equipment) {
            Equipment equip = (Equipment) entity;
            if (equip.getId() == null) {
                equip.setId(equipmentIdCounter.incrementAndGet());
            }
            equipment.put(equip.getId(), equip);
            return entity;
        } else if (entity instanceof Loan) {
            Loan loan = (Loan) entity;
            if (loan.getId() == null) {
                loan.setId(loanIdCounter.incrementAndGet());
            }
            loans.put(loan.getId(), loan);
            return entity;
        }
        return null;
    }

    public Optional<Student> findStudentById(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    public Optional<Equipment> findEquipmentById(Long id) {
        return Optional.ofNullable(equipment.get(id));
    }

    public Optional<Loan> findLoanById(Long id) {
        return Optional.ofNullable(loans.get(id));
    }

    public List<Equipment> findAllAvailableEquipment() {
        return equipment.values().stream()
                .filter(Equipment::isAvailability)
                .collect(Collectors.toList());
    }

    public List<Loan> findActiveLoansByStudentId(Long studentId) {
        return loans.values().stream()
                .filter(loan -> loan.getStudent().getId().equals(studentId) && loan.getStatus() == LoanStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}