package ru.yandex.practicum.event.publicAPI;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.event.EventState;
import ru.yandex.practicum.event.Location;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventsRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            "WHERE e.description LIKE ?1 " +
            "AND e.category.id IN ?2 " +
            "AND e.paid = ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5 " +
            "AND e.participantLimit > ?6 " +
            "ORDER BY ?7")
    List<Event> findAvailableEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                           LocalDateTime rangeEnd, Integer onlyAvailable, String sort);

    @Query("SELECT e FROM Event e " +
            "WHERE e.description LIKE ?1 " +
            "AND e.category.id IN ?2 " +
            "AND e.paid = ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5 " +
            //"AND e.participantLimit > ?6 " +
            "ORDER BY ?6")
    List<Event> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, String sort);


}