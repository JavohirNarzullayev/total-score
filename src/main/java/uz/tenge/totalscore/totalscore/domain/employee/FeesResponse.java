package uz.tenge.totalscore.totalscore.domain.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class FeesResponse {
    private BigInteger id;
    private String fio;
    private BigDecimal result;
    private List<FeesResponse> fees;

    public FeesResponse(BigInteger id, String fio, BigDecimal result) {
        this.id = id;
        this.fio = fio;
        this.result = result;
    }

    public FeesResponse(CalculateResponse pGet) {
        this.id = pGet.getId();
        this.fio = pGet.getFio();
        this.result = pGet.getResult().abs();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeesResponse that = (FeesResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(fio, that.fio) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, result);
    }

}
