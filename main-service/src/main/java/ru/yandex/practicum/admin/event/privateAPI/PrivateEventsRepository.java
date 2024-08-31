package ru.yandex.practicum.admin.event.privateAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.admin.event.Event;

import java.util.List;

public interface PrivateEventsRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorId(int userId);


}