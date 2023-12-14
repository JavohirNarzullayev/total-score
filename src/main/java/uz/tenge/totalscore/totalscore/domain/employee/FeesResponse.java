package uz.tenge.totalscore.totalscore.domain.employee;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
public class FeesResponse {
    private BigInteger id;
    private String fio;
    private BigDecimal result;
    public List<Loan> fees;

    @Getter
    @Setter
    public static class Loan {
        private BigInteger id;
        private String fio;
        private BigDecimal amount;
    }

}
