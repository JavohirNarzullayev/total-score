package uz.tenge.totalscore.totalscore.domain.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventPayload {
    private String name;
    private BigDecimal amount;
}
