package uz.tenge.totalscore.totalscore.domain.payment;

import lombok.Data;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeResponse;
import uz.tenge.totalscore.totalscore.domain.event.EventResponse;

import java.math.BigDecimal;

@Data
public class PaymentResponse {
    private Long id;
    private String name;
    private EventResponse event;
    private EmployeeResponse employee;
    private BigDecimal amount;
}
