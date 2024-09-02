package ru.yandex.practicum.event.publicAPI;

import ru.yandex.practicum.admin.event.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventsService {

    List<Event> getEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                          LocalDateTime rangeEnd, Boolean onlyAvailable, String sort);

    Event getEventById(int eventId);
}