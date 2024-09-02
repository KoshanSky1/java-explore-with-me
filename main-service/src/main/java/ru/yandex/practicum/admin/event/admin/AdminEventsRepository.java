package ru.yandex.practicum.admin.event.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.event.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventsRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id IN ?1 " +
            "AND e.state IN ?2 " +
            "AND e.category.id IN ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5")
    List<Event> findEvents(List<Long> users, List<EventState> states, List<Long> categories,
                           LocalDateTime rangeStart, LocalDateTime rangeEnd);

}