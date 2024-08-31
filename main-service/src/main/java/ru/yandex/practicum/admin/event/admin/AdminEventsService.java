package ru.yandex.practicum.admin.event.admin;

import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.UpdateEventAdminRequest;

import java.util.List;

public interface AdminEventsService {

    List<Event> getEvents(int[] users, String[] states, int[] categories, String rangeStart, String rangeEnd);

    UpdateEventAdminRequest updateEventById(int eventId, UpdateEventAdminRequest updateEventAdminRequest);

}
