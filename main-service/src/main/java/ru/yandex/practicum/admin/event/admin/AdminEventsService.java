package ru.yandex.practicum.admin.event.admin;

import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventsService {

    List<Event> getEvents(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                          LocalDateTime rangeEnd);

    UpdateEventAdminRequest updateEventById(int eventId, UpdateEventAdminRequest updateEventAdminRequest);

}
