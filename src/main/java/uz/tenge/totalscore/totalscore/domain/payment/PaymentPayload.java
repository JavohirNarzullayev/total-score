package uz.tenge.totalscore.totalscore.domain.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentPayload {
    private Long eventId;
    private Long employeeId;
    private BigDecimal amount;
}
