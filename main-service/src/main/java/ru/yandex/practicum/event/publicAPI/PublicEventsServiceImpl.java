package ru.yandex.practicum.event.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.event.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicEventsServiceImpl implements PublicEventsService {
    private final PublicEventsRepository repository;

    @Override
    public List<Event> getEvents(String text, int[] categories, Boolean paid, String rangeStart,
                                 String rangeEnd, Boolean onlyAvailable, String sort) {

        List<Long> categoryList = new ArrayList<>();

        for (Integer c : categories) {
            categoryList.add((long)c);
        }

        String datePattern = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime rs = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern(datePattern));
        LocalDateTime re = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern(datePattern));

        return repository.findEvents(text, categoryList, paid, rs, re, onlyAvailable, sort);
    }

    @Override
    public Event getEventById(int eventId) {
        return repository.findById((long) eventId).orElseThrow();
    }

}