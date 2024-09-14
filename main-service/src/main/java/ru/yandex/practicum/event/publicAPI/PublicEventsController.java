package ru.yandex.practicum.event.publicAPI;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.model.search.SearchEventsArgs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.event.dto.EventMapper.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    private final PublicEventsService service;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getEvents(@RequestParam(required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeStart,
                                                        @RequestParam(required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeEnd,
                                                        @RequestParam(required = false) String text,
                                                        @RequestParam(required = false) List<Long> categories,
                                                        @RequestParam(required = false) Boolean paid,
                                                        @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                        @RequestParam(defaultValue = "10") @Positive Integer size,
                                                        HttpServletRequest request) {

        log.info("---START GET EVENTS ENDPOINT---");

        SearchEventsArgs args = toSearchEventsArgs(rangeStart, rangeEnd, text, categories, paid, onlyAvailable, sort,
                request);

        return new ResponseEntity<>(pagedResponse(service.getEvents(args), from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable int eventId, HttpServletRequest request) {

        log.info("---START GET EVENT BY ID ENDPOINT---");

        return new ResponseEntity<>(toEventFullDto(service.getEventById(eventId, request)), HttpStatus.OK);
    }

    private List<EventFullDto> pagedResponse(List<Event> events, int from, int size) {
        List<EventFullDto> pagedEvents = new ArrayList<>();

        int totalEvents = events.size();
        int toIndex = from + size;

        if (from <= totalEvents) {
            if (toIndex > totalEvents) {
                toIndex = totalEvents;
            }
            for (Event event : events.subList(from, toIndex)) {
                pagedEvents.add(toEventFullDto(event));
            }
            return pagedEvents;
        } else {
            return Collections.emptyList();
        }
    }
}