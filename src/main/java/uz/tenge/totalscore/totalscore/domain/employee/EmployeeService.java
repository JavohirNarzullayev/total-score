package uz.tenge.totalscore.totalscore.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.tenge.totalscore.totalscore.repository.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
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
