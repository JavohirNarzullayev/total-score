package uz.tenge.totalscore.totalscore.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.tenge.totalscore.totalscore.domain.employee.*;

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
