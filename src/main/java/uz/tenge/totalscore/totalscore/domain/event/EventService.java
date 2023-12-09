package uz.tenge.totalscore.totalscore.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.tenge.totalscore.totalscore.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event getById(Long id) {
        return findId(id).orElseThrow();
    }

    public Optional<Event> findId(Long id) {
        return eventRepository.findById(id);
    }

    public Event update(Long id, EventPayload payload) {
        Event event = getById(id);
        event.setName(payload.getName());
        return eventRepository.save(event);
    }

    public Event save(EventPayload payload) {
        Event event = new Event();
        event.setName(payload.getName());
        return eventRepository.save(event);
    }
}
