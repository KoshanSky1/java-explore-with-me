package ru.yandex.practicum.event.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.dto.UpdateEventAdminRequest;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.search.SearchPublicEventsArgs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.event.dto.EventMapper.toEventFullDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventsController {
    private final AdminEventsService service;

    public ResponseEntity<List<EventFullDto>> getAll(@RequestParam(required = false) List<Long> users,
                                                     @RequestParam(required = false) List<String> states,
                                                     @RequestParam(required = false) List<Long> categories,
                                                     @RequestParam(required = false) LocalDateTime rangeStart,
                                                     @RequestParam(required = false) LocalDateTime rangeEnd,
                                                     @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                     @RequestParam(defaultValue = "10") @Positive int size) {

        log.info("---START GET EVENTS ENDPOINT---");

        List<EventState> statesEnum = null;
        if (states != null) {
            List<EventState> eventStates = findByState(states);
        }

        SearchPublicEventsArgs args = SearchPublicEventsArgs.builder()
                .users(users)
                .states(statesEnum)
                .categories(categories)
                .rangeStart(LocalDateTime.now())
                .rangeEnd(LocalDateTime.now())
                .build();

        List<Event> events = service.getEvents(args);

        return new ResponseEntity<>(pagedResponse(events, from, size), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventById(@PathVariable int eventId,
                                                        @RequestBody @Valid UpdateEventAdminRequest
                                                                updateEventAdminRequest) {
        log.info("---START UPDATE EVENT BY ID ENDPOINT---");
        return new ResponseEntity<>(toEventFullDto(service.updateEventById(eventId, updateEventAdminRequest)),
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

    public List<EventState> findByState(List<String> states) {
        List<EventState> eventStates = new ArrayList<>();
        for (String s : states) {
            for (EventState state : EventState.values()) {
                if (s.equalsIgnoreCase(state.name())) {
                    eventStates.add(state);
                }
            }
        }
        return eventStates;
    }

}