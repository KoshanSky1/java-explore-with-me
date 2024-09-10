package ru.yandex.practicum.event.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAll(Specification<Event> spec);

    boolean existsByCategory(Category category);

    List<Event> findAllByInitiatorId(int userId);

    Collection<? extends Event> findAllByIdIn(Set<Long> eventIds);
}