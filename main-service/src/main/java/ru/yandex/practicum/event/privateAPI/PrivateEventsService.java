package ru.yandex.practicum.event.privateAPI;

import ru.yandex.practicum.event.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface PrivateEventsService {
    List<Event> getEventsByUser(int userId);

    Event addEvent(int userId, NewEventDto newEventDto);

    Event getEventById(int userId, int eventId);

    Event updateEventById(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    List<Request> getRequests(int userId, int eventId);

    EventRequestStatusUpdateResult updateRequests(int userId, int eventId, EventRequestStatusUpdateRequest request);
}
