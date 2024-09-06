package ru.yandex.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.enums.EventStatus;
import ru.yandex.practicum.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventId(int eventId);

    List<Request> findAllByRequesterId(int userId);

    Request findByIdAndRequesterId(int requestId, int userId);

    List<Request> findAllByIdInAndStatus(List<Integer> requestIds, EventStatus status);

    boolean existsByRequesterIdAndEventId(int userId, int eventId);

}
