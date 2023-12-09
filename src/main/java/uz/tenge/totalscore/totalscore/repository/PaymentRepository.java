package uz.tenge.totalscore.totalscore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tenge.totalscore.totalscore.domain.employee.Employee;
import uz.tenge.totalscore.totalscore.domain.payment.Payment;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findAllByEventId(Long eventId);
}
