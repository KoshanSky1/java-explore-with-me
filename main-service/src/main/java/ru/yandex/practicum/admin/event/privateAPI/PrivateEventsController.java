package ru.yandex.practicum.admin.event.privateAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.NewEventDto;
import ru.yandex.practicum.event.*;
import ru.yandex.practicum.request.Request;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.admin.event.EventMapper.toEventFullDto;
import static ru.yandex.practicum.admin.event.EventMapper.toEventShortDto;
import static ru.yandex.practicum.request.RequestMapper.toParticipationRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {
    private final PrivateEventsService service;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEventsByUser(@PathVariable int userId,
                                                               @RequestParam(defaultValue = "0") int from,
                                                               @RequestParam(defaultValue = "10") int size) {
        log.info("---START GET EVENTS BY USER ENDPOINT---");
        return new ResponseEntity<>(pagedResponse(service.getEventsByUser(userId), from, size),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventFullDto> addEvent(@PathVariable int userId,
                                                 @RequestBody @Valid NewEventDto newEventDto) {
        log.info("---START ADD EVENT ENDPOINT---");
        return new ResponseEntity<>(toEventFullDto(service.addEvent(userId, newEventDto)), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable int userId, @PathVariable int eventId) {
        log.info("---START GET EVENT BY ID ENDPOINT---");
        return new ResponseEntity<>(toEventFullDto(service.getEventById(userId, eventId).orElseThrow()), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventById(@PathVariable int userId,
                                                        @PathVariable int eventId,
                                                        @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("---START UPDATE EVENT BY ID ENDPOINT---");
        return new ResponseEntity<>(toEventFullDto(service.updateEventById(userId, eventId, updateEventUserRequest)),
                HttpStatus.OK);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequests(@PathVariable int userId,
                                                                     @PathVariable int eventId) {
        log.info("---START GET REQUESTS ENDPOINT---");
        List<Request> requests = service.getRequests(userId, eventId);
        List<ParticipationRequestDto> participationRequestsDto = new ArrayList<>();
        for (Request request : requests) {
            participationRequestsDto.add(toParticipationRequestDto(request));
        }
        return new ResponseEntity<>(participationRequestsDto, HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateRequest> updateRequests(@PathVariable int userId,
                                                                          @PathVariable int eventId,
                                                                          @RequestBody EventRequestStatusUpdateRequest
                                                                                  eventRequestStatusUpdateRequest) {
        log.info("---START UPDATE REQUESTS ENDPOINT---");

        service.updateRequests(userId, eventId, eventRequestStatusUpdateRequest);
        return new ResponseEntity<>(eventRequestStatusUpdateRequest, HttpStatus.OK);
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