package ru.yandex.practicum.event.publicAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.admin.event.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventsRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            "WHERE e.description CONTAINS ?1 " +
            "AND e.category.id IN ?2 " +
            "AND e.paid = ?3 " +
            "AND e.date BETWEEN ?4 AND ?5" +
            "AND e.participant_limit = true" +
            "ORDER BY ?7")
    List<Event> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                           LocalDateTime rangeEnd, Boolean onlyAvailable, String sort);
}