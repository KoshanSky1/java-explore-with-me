package ru.yandex.practicum.admin.event.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.UpdateEventAdminRequest;
import ru.yandex.practicum.event.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.admin.event.EventMapper.toEventFullDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventsController {
    private final AdminEventsService service;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getEvents(@RequestParam int[] users,
                                                        @RequestParam String[] states,
                                                        @RequestParam int[] categories,
                                                        @RequestParam String rangeStart,
                                                        @RequestParam String rangeEnd,
                                                        @RequestParam(defaultValue = "0") int from,
                                                        @RequestParam(defaultValue = "10") int size) {
        log.info("---START GET EVENTS ENDPOINT---");
        return new ResponseEntity<>(pagedResponse(service.getEvents(users, states, categories, rangeStart, rangeEnd),
                from, size),
                HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<UpdateEventAdminRequest> updateEventById(@PathVariable int eventId,
                                                                   @RequestBody UpdateEventAdminRequest
                                                                           updateEventAdminRequest) {
        log.info("---START UPDATE EVENT BY ID ENDPOINT---");
        return new ResponseEntity<>(service.updateEventById(eventId, updateEventAdminRequest),
                HttpStatus.OK);
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