package uz.tenge.totalscore.totalscore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tenge.totalscore.totalscore.domain.employee.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
