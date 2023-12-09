package uz.tenge.totalscore.totalscore.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeMapper;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeePayload;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeResponse;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employee")
@Tag(name = "Employee resource",description = "Manage employee")
public class EmployeeResource {
    private final EmployeeService employeeService;

    @GetMapping("/list")
    public List<EmployeeResponse> events(){
        return EmployeeMapper.toResponse(employeeService.getEvents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id")
    public EmployeeResponse getById(
            @PathVariable Long id
    ){
        return EmployeeMapper.toResponse(employeeService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create new employee")
    public void create(
            @RequestBody EmployeePayload payload
    ){
        employeeService.save(payload);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update employee")
    public EmployeeResponse update(
            @PathVariable Long id,
            @RequestBody EmployeePayload payload
    ){
        return EmployeeMapper.toResponse(employeeService.update(id, payload));
    }
}
