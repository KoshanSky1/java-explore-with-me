package ru.yandex.practicum.event.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, PublicSelectionRepository {

    //@Query("SELECT e FROM Event e " +
    //        "WHERE e.initiator.id IN (:users) " +
    //        "AND e.state IN (:states) " +
     //       "AND e.category.id IN (:categories) " +
     //       "AND e.date BETWEEN :rangeStart AND :rangeEnd")
    //List<Event> getEvents(
    //        @Param("users") List<Long> users,
     //       @Param("states") List<EventState> states,
    //        @Param("categories") List<Long> categories,
      //      @Param("rangeStart") LocalDateTime rangeStart,
      //      @Param("rangeEnd") LocalDateTime rangeEnd);

    List<Event> findAll(Specification<Event> spec);

    boolean existsByCategory(Category category);

    List<Event> findAllByInitiatorId(int userId);

}