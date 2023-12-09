package uz.tenge.totalscore.totalscore.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.tenge.totalscore.totalscore.domain.employee.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/calculate")
public class CalculateResource {
    private final EmployeeService employeeService;

    @GetMapping("/list")
    public List<CalculateResponse> calculate(){
        return employeeService.getCalculateResult();
    }

}
