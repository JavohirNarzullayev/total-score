package uz.tenge.totalscore.totalscore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tenge.totalscore.totalscore.domain.event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
}
