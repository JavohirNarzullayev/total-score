package uz.tenge.totalscore.totalscore.web.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.tenge.totalscore.totalscore.domain.event.EventService;

@RestController
@RequiredArgsConstructor
public class EventResource {
    private final EventService eventService;
}
