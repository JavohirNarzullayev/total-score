package uz.tenge.totalscore.totalscore.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.tenge.totalscore.totalscore.domain.event.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/event")
public class EmployeeResource {
    private final EventService eventService;

    @GetMapping("/list")
    public List<?> events(){
        return eventService.getEvents();
    }

    @GetMapping("/{id}")
    public Object getById(
            @PathVariable Long id
    ){
        return eventService.getById(id);
    }
}
