package ru.yandex.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, PublicSelectionRepository {

    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id IN ?1 " +
            "AND e.state IN ?2 " +
            "AND e.category.id IN ?3 " +
            "AND e.date BETWEEN ?4 AND ?5")
    List<Event> findEvents(List<Long> users, List<EventState> states, List<Long> categories,
                           LocalDateTime rangeStart, LocalDateTime rangeEnd);

    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id IN ?1 " +
            //"AND e.state IN ?2 " +
            "AND e.category.id IN ?2 " +
            "AND e.date BETWEEN ?3 AND ?4")
    List<Event> findEventsWithoutStates(List<Long> users, List<Long> categories,
                           LocalDateTime rangeStart, LocalDateTime rangeEnd);

    boolean existsByCategory(Category category);

    List<Event> findAllByInitiatorId(int userId);

}