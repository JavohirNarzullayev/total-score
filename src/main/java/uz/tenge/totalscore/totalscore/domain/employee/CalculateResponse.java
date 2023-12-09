package uz.tenge.totalscore.totalscore.domain.employee;

import lombok.Data;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class CalculateResponse {
    private BigInteger id;
    private String fio;
    private BigDecimal paid;
    private BigDecimal result;

    public CalculateResponse(Tuple tuple) {
        this.id = tuple.get("id", BigInteger.class);
        this.fio = tuple.get("fio", String.class);
        this.paid = tuple.get("paid", BigDecimal.class);
        this.result = tuple.get("result", BigDecimal.class);
    }
}
