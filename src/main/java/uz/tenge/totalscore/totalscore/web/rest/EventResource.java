package uz.tenge.totalscore.totalscore.web.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.tenge.totalscore.totalscore.domain.event.EventMapper;
import uz.tenge.totalscore.totalscore.domain.event.EventPayload;
import uz.tenge.totalscore.totalscore.domain.event.EventResponse;
import uz.tenge.totalscore.totalscore.domain.event.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/event")
public class EventResource {
    private final EventService eventService;

    @GetMapping("/list")
    public List<EventResponse> events(){
        return EventMapper.toResponse(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public EventResponse getById(@PathVariable Long id){
        return EventMapper.toResponse(eventService.getById(id));
    }

    @PostMapping
    public EventResponse create(
            @RequestBody EventPayload payload
    ){
        return EventMapper.toResponse(eventService.save(payload));
    }

    @PutMapping("/{id}")
    public EventResponse update(
            @PathVariable Long id,
            @RequestBody EventPayload payload
    ) {
        return EventMapper.toResponse(eventService.update(id, payload));
    }
//SELECT
}
