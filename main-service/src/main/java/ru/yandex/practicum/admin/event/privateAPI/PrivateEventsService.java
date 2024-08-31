package ru.yandex.practicum.admin.event.privateAPI;

import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.NewEventDto;
import ru.yandex.practicum.event.*;
import ru.yandex.practicum.request.Request;

import java.util.List;
import java.util.Optional;

public interface PrivateEventsService {
    List<Event> getEventsByUser(int userId);

    Event addEvent(int userId, NewEventDto newEventDto);

    Optional<Event> getEventById(int userId, int eventId);

    Event updateEventById(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    List<Request> getRequests(int userId, int eventId);

    List<Request> updateRequests(int userId, int eventId, EventRequestStatusUpdateRequest request);
}
