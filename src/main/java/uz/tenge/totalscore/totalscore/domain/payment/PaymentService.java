package uz.tenge.totalscore.totalscore.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeService;
import uz.tenge.totalscore.totalscore.domain.event.Event;
import uz.tenge.totalscore.totalscore.domain.event.EventService;
import uz.tenge.totalscore.totalscore.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final EventService eventService;
    private final EmployeeService employeeService;

    public List<Payment> getPaymentsByEventId(Long eventId) {
        return paymentRepository.findAllByEventId(eventId);
    }

    public Payment getById(Long id) {
        return findId(id).orElseThrow();
    }

    public Optional<Payment> findId(Long id) {
        return paymentRepository.findById(id);
    }

    @Transactional
    public Payment save(PaymentPayload payload) {
        var event = eventService.getById(payload.getEventId());
        eventService.payToEvent(event,payload.getAmount());

        var payment = new Payment();
        payment.setAmount(payload.getAmount());
        payment.setEmployee(employeeService.getById(payload.getEmployeeId()));
        payment.setEvent(event);
        payment.setAmount(payload.getAmount());
        return paymentRepository.save(payment);
    }
}
