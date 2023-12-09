package uz.tenge.totalscore.totalscore.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tenge.totalscore.totalscore.repository.EventRepository;

import java.math.BigDecimal;
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
        var event = getById(id);
        event.setName(payload.getName());
        event.setAmount(payload.getAmount());
        return eventRepository.save(event);
    }

    @Transactional
    public void payToEvent(Event event, BigDecimal amount){
        if (event.sum().compareTo(amount) < 0) {
            throw new RuntimeException("The payment amount  shouldn't be greater than event payment");
        }
        event.pay(amount);
        eventRepository.save(event);
    }

    public Event save(EventPayload payload) {
        var event = new Event();
        event.setName(payload.getName());
        event.setAmount(payload.getAmount());
        return eventRepository.save(event);
    }
}
