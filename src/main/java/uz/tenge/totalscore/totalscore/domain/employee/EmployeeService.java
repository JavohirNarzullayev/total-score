package uz.tenge.totalscore.totalscore.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.tenge.totalscore.totalscore.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public List<Employee> getEvents() {
        return employeeRepository.findAll();
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
