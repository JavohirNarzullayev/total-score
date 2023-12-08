package uz.tenge.totalscore.totalscore.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.tenge.totalscore.totalscore.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<?> getEvents() {
        return null;
    }

    public Object getById(Long id) {
        return null;
    }
}
