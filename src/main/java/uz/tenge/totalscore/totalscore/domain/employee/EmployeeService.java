package uz.tenge.totalscore.totalscore.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.tenge.totalscore.totalscore.repository.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EntityManager em;

    public List<Employee> getEmployee() {
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
        List<CalculateResponse> calculateResponses = getCalculateResult(eventId);
        if (calculateResponses.isEmpty()) return new ArrayList<>();
        List<Employee> employee = getEmployee();

        LinkedList<FeesResponse> haveToGets = new LinkedList<>();
        LinkedList<FeesResponse> haveToGives = new LinkedList<>();

        for (var payment : calculateResponses) {
            if (payment.getResult().compareTo(BigDecimal.ZERO) > 0) {
                haveToGets.add(new FeesResponse(payment));
            } else if (payment.getResult().compareTo(BigDecimal.ZERO) < 0) {
                haveToGives.add(new FeesResponse(payment));
            }
        }
        System.out.println(haveToGets);//minus
        System.out.println(haveToGives);// plus
        System.out.println("####");

        HashMap<BigInteger, LinkedList<FeesResponse>> transactions = new HashMap<>();

        FeesResponse pGet = haveToGets.removeFirst();
        FeesResponse pGive = haveToGives.removeFirst();

        BigDecimal negate = new BigDecimal(-1);
        do {
            FeesResponse finalPGive = pGive;
            FeesResponse finalPGet = pGet;

            int c = pGet.getResult().compareTo(pGive.getResult());
            if (c == 0) { // get == give
                transactions.compute(pGet.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(finalPGive);
                    return list;
                });
                transactions.compute(pGive.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGet.getId(),finalPGet.getFio(), finalPGet.getResult().multiply(negate)));
                    return list;
                });
                pGet = haveToGets.isEmpty() ? null : haveToGets.removeFirst();
                pGive = haveToGives.isEmpty() ? null : haveToGives.removeFirst();
            } else if (c < 0) { // get < give
                transactions.compute(pGet.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGive.getId(),finalPGive.getFio(), finalPGet.getResult()));
                    return list;
                });
                transactions.compute(pGive.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGet.getId(),finalPGet.getFio(), finalPGet.getResult().multiply(negate)));
                    return list;
                });
                pGive = new FeesResponse(pGive.getId(),pGive.getFio(), pGive.getResult().subtract(pGet.getResult()));
                pGet = haveToGets.isEmpty() ? null : haveToGets.removeFirst();
            } else { // get > give
                transactions.compute(pGet.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(finalPGive);
                    return list;
                });
                transactions.compute(pGive.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGet.getId(),finalPGet.getFio(), finalPGive.getResult().multiply(negate)));
                    return list;
                });
                pGet = new FeesResponse(pGet.getId(),pGet.getFio(), pGet.getResult().subtract(pGive.getResult()));
                pGive = haveToGives.isEmpty() ? null : haveToGives.removeFirst();
            }
        } while (pGet != null && pGive != null);


        List<FeesResponse> feesResponses = new ArrayList<>();
        employee.forEach(employee1 -> {
            var response = new FeesResponse(
                    BigInteger.valueOf(employee1.getId()),
                    employee1.getFio(),
                    null
            );
            LinkedList<FeesResponse> orDefault = transactions.getOrDefault(
                    BigInteger.valueOf(employee1.getId()),
                    new LinkedList<>());
            response.setFees(orDefault);
            feesResponses.add(response);
        });

        return feesResponses;
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
