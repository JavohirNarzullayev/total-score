package uz.tenge.totalscore.totalscore.domain.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import uz.tenge.totalscore.totalscore.repository.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EntityManager em;

    public List<Employee> getEvents() {
        return employeeRepository.findAll();
    }

    @SuppressWarnings("all")
    public List<CalculateResponse> getCalculateResult(Long eventId) {
        String condition = "";
        String sql = """
                SELECT e.ID,
                       e.FIO,
                       coalesce(sum(p.amount), 0)                                                         as paid,
                       avg(coalesce(sum(p.amount), 0)) OVER (PARTITION BY 1)                              as total_avg,
                       coalesce(sum(p.amount), 0) - avg(coalesce(sum(p.amount), 0)) OVER (PARTITION BY 1) as result
                FROM employee e
                LEFT JOIN payment p on e.id = p.employee_id $condition
                GROUP BY e.id;
                """;
        if (eventId != null) condition += "and p.event_id=:eventId";
        sql = sql.replace("$condition", condition);
        var query = em.createNativeQuery(sql, Tuple.class);
        if (eventId !=null) query.setParameter("eventId", eventId);

        var resultList = (List<Tuple>) query.getResultList();
        return resultList.stream()
                .map(CalculateResponse::new)
                .toList();
    }



    public List<FeesResponse> getFees(Long eventId) {
        var list = getCalculateResult(eventId);
        var tempAmount = BigDecimal.ZERO;
        List<FeesResponse> dtos = new ArrayList<>();
        for (var response : list) {
            var dto = new FeesResponse();
            dto.setId(response.getId());
            dto.setFio(response.getFio());
            dto.setResult(response.getResult());

            tempAmount = response.getResult();
            List<FeesResponse.Loan> fees = new ArrayList<>();

            var participants = list.stream()
                    .filter(calculateResponse -> !calculateResponse.getId().equals(response.getId()))
                    .toList();
            for (var participant : participants) {
                var fee = new FeesResponse.Loan();
                fee.setId(participant.getId());
                fee.setFio(participant.getFio());

                var feeTo = BigDecimal.ZERO;

                int compareTo = tempAmount.compareTo(BigDecimal.ZERO);
                int participantCompare = participant.getResult().compareTo(BigDecimal.ZERO);

                if (compareTo < 0 && participantCompare > 0) {
                    var add = tempAmount.add(participant.getResult());
                    if (add.compareTo(BigDecimal.ZERO) > 0) {
                        feeTo = tempAmount;
                        tempAmount = BigDecimal.ZERO;
                    } else {
                        feeTo = participant.getResult();
                        tempAmount = add;
                    }
                }
                if (compareTo > 0 && participantCompare < 0) {
                    var add = tempAmount.add(participant.getResult());
                    if (add.compareTo(BigDecimal.ZERO) > 0) {
                        tempAmount = add;
                        feeTo = participant.getResult();
                    } else {
                        feeTo = tempAmount.multiply(BigDecimal.valueOf(-1));
                        tempAmount = BigDecimal.ZERO;
                    }
                }
                fee.setAmount(feeTo);
                fees.add(fee);
            }
            dto.setFees(fees);
            dtos.add(dto);
        }
        return dtos;
    }

    public Employee getById(Long id) {
        return findId(id).orElseThrow();
    }

    public Optional<Employee> findId(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee save(EmployeePayload payload) {
        Employee employee = new Employee();
        employee.setFio(payload.getFio());
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, EmployeePayload payload) {
        Employee employee = getById(id);
        employee.setFio(payload.getFio());
        return employeeRepository.save(employee);
    }
}
