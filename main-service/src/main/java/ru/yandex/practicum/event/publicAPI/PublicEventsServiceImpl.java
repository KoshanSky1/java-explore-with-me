package ru.yandex.practicum.event.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.error.IncorrectParameterException;
import ru.yandex.practicum.error.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicEventsServiceImpl implements PublicEventsService {
    private final PublicEventsRepository repository;

    @Override
    public List<Event> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                 String rangeEnd, Boolean onlyAvailable, String sort) {

        String datePattern = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime rs = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern(datePattern));
        LocalDateTime re = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern(datePattern));

        if (re.isBefore(LocalDateTime.now()) || rs.isBefore(LocalDateTime.now())) {
            throw new IncorrectParameterException("Failed to convert value of type java.lang.String to required type " +
            "long; nested exception is java.lang.NumberFormatException: For input string: ad");
        }
        if (text == null) {
            text = "";
        }

        if (onlyAvailable) {
            return repository.findAvailableEvents(text, categories, paid, rs, re, 0, sort);
        } else {
            return repository.findEvents(text, categories, paid, rs, re, sort);
        }
    }

    @Override
    public Event getEventById(int eventId) {
        return repository.findById((long) eventId).orElseThrow(NotFoundException::new);
    }

}