package uz.tenge.totalscore.totalscore.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.tenge.totalscore.totalscore.domain.employee.CalculateResponse;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeService;
import uz.tenge.totalscore.totalscore.domain.employee.FeesResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/calculate")
@Tag(name = "Calculate resource")
public class CalculateResource {
    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Fee employee")
    public List<CalculateResponse> calculate(
            @RequestParam(required = false) Long eventId
    ){
        return employeeService.getCalculateResult(eventId);
    }

    @GetMapping("/fees")
    @Operation(summary = "Each employee fee")
    public List<FeesResponse> perCalculate(
            @RequestParam(required = false) Long eventId
    ){
        return employeeService.getFees(eventId);
    }


}
