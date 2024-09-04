package ru.yandex.practicum.event.admin;

import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.dto.UpdateEventAdminRequest;
import ru.yandex.practicum.event.model.search.SearchPublicEventsArgs;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventsService {

    List<Event> getEvents(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                          LocalDateTime rangeEnd);

    Event updateEventById(int eventId, UpdateEventAdminRequest updateEventAdminRequest);

}
