package ru.yandex.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.search.SelectionCriteria;

@Repository
public interface PublicSelectionRepository {

    Page<Event> findAllWithArgs(Pageable pageable, SelectionCriteria selectionCriteria);

}
