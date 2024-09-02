package ru.yandex.practicum.event.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.event.Event;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicEventsServiceImpl implements PublicEventsService {
    private final PublicEventsRepository repository;

    @Override
    public List<Event> getEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd, Boolean onlyAvailable, String sort) {
        return repository.findEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort);
    }

    @Override
    public Event getEventById(int eventId) {
        return repository.findById((long) eventId).orElseThrow();
    }

}