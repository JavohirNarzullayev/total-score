package uz.tenge.totalscore.totalscore.domain.employee;


import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class EmployeeMapper {

    public static EmployeeResponse toResponse(Employee employee) {
        if (employee == null) return null;
        var response = new EmployeeResponse();
        response.setFio(employee.getFio());
        response.setId(employee.getId());
        return response;
    }


    public static List<EmployeeResponse> toResponse(List<Employee> employee) {
        if (employee == null) return new ArrayList<>();
        return employee.stream()
                .map(EmployeeMapper::toResponse)
                .filter(Objects::nonNull)
                .toList();
    }
}
