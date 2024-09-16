package ru.yandex.practicum.event.publicAPI;

import jakarta.servlet.http.HttpServletRequest;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.search.SearchEventsArgs;

import java.util.List;

public interface PublicEventsService {
    List<Event> getEvents(SearchEventsArgs args);

    Event getEventById(int eventId, HttpServletRequest request);

    Event getEventByIdWithoutRequest(int eventId);
}