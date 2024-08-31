package ru.yandex.practicum.event.publicAPI;

import ru.yandex.practicum.admin.event.Event;

import java.util.List;

public interface PublicEventsService {

    List<Event> getEvents(String text, int[] categories, Boolean paid, String rangeStart, String rangeEnd,
                          Boolean onlyAvailable, String sort);

    Event getEventById(int eventId);
}