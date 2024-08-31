package ru.yandex.practicum.event.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.event.EventFullDto;
import ru.yandex.practicum.event.EventShortDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.admin.event.EventMapper.toEventFullDto;
import static ru.yandex.practicum.admin.event.EventMapper.toEventShortDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    private final PublicEventsService service;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEvents(@RequestParam(required = false) String text,
                                                         @RequestParam(required = false) int[] categories,
                                                         @RequestParam(required = false) Boolean paid,
                                                         @RequestParam(required = false) String rangeStart,
                                                         @RequestParam(required = false) String rangeEnd,
                                                         @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                         @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                                         @RequestParam(defaultValue = "0") int from,
                                                         @RequestParam(defaultValue = "10") int size) {
        log.info("---START GET EVENTS ENDPOINT---");
        return new ResponseEntity<>(pagedResponse(service.getEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort), from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable int eventId) {
        log.info("---START GET EVENT BY ID ENDPOINT---");
        return new ResponseEntity<>(toEventFullDto(service.getEventById(eventId)), HttpStatus.OK);
    }

    private List<EventShortDto> pagedResponse(List<Event> events, int from, int size) {
        List<EventShortDto> pagedEvents = new ArrayList<>();
        int totalEvents = events.size();
        int toIndex = from + size;

        if (from <= totalEvents) {
            if (toIndex > totalEvents) {
                toIndex = totalEvents;
            }
            for (Event event : events.subList(from, toIndex)) {
                pagedEvents.add(toEventShortDto(event));
            }
            return pagedEvents;
        } else {
            return Collections.emptyList();
        }
    }

}