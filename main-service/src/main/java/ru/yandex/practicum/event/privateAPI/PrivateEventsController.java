package ru.yandex.practicum.event.privateAPI;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.event.comment.Comment;
import ru.yandex.practicum.event.comment.commentAPI.CommentService;
import ru.yandex.practicum.event.comment.dto.CommentDto;
import ru.yandex.practicum.event.comment.dto.NewCommentDto;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.*;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.model.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.event.comment.CommentMapper.toCommentDto;
import static ru.yandex.practicum.request.dto.RequestMapper.toParticipationRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {

    private final PrivateEventsService service;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEventsByUser(@PathVariable int userId,
                                                               @RequestParam(defaultValue = "0") int from,
                                                               @RequestParam(defaultValue = "10") int size) {

        log.info("---START GET EVENTS BY USER ENDPOINT---");

        return new ResponseEntity<>(pagedResponse(service.getEventsByUser(userId), from, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventFullDto> addEvent(@PathVariable int userId,
                                                 @RequestBody @Valid NewEventDto newEventDto) {

        log.info("---START ADD EVENT ENDPOINT---");

        EventFullDto eventFullDto = EventMapper.toEventFullDto(service.addEvent(userId, newEventDto));

        return new ResponseEntity<>(eventFullDto, HttpStatus.CREATED);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable int userId, @PathVariable int eventId) {

        log.info("---START GET EVENT BY ID ENDPOINT---");

        return new ResponseEntity<>(EventMapper.toEventFullDto(service.getEventById(userId, eventId)), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventById(@PathVariable int userId,
                                                        @PathVariable int eventId,
                                                        @RequestBody @Valid UpdateEventUserRequest
                                                                updateEventUserRequest) {

        log.info("---START UPDATE EVENT BY ID ENDPOINT---");

        return new ResponseEntity<>(EventMapper.toEventFullDto(service.updateEventById(userId, eventId,
                updateEventUserRequest)), HttpStatus.OK);
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
    public ResponseEntity<EventRequestStatusUpdateResult> updateRequests(@PathVariable int userId,
                                                                         @PathVariable int eventId,
                                                                         @RequestBody EventRequestStatusUpdateRequest
                                                                                 eventRequestStatusUpdateRequest) {

        log.info("---START UPDATE REQUESTS ENDPOINT---");

        return new ResponseEntity<>(service.updateRequests(userId, eventId, eventRequestStatusUpdateRequest),
                HttpStatus.OK);
    }

    @PostMapping("/{eventId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable int userId, @PathVariable int eventId,
                                                    @RequestBody @Valid NewCommentDto newCommentDto) {

        log.info("---START CREATE COMMENT ENDPOINT---");

        return new ResponseEntity<>(toCommentDto(commentService.addNewComment(userId, eventId, newCommentDto)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{eventId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int userId, @PathVariable int eventId,
                                                    @PathVariable int commentId,
                                                    @RequestBody NewCommentDto newCommentDto) {

        log.info("---START UPDATE COMMENT ENDPOINT---");

        return new ResponseEntity<>(toCommentDto(commentService.updateComment(userId, eventId, commentId, newCommentDto)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int userId, @PathVariable int eventId,
                                              @PathVariable int commentId) {

        log.info("---START DELETE COMMENT ENDPOINT---");

        commentService.deleteComment(userId, eventId, commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{eventId}/comment")
    public ResponseEntity<List<CommentDto>> getAllCommentsByEventId(@PathVariable int userId,
                                                                    @PathVariable int eventId,
                                                                    @RequestParam(required = false) String sort,
                                                                    @RequestParam(defaultValue = "0")
                                                                    @PositiveOrZero Integer from,
                                                                    @RequestParam(defaultValue = "10")
                                                                    @Positive Integer size,
                                                                    HttpServletRequest request) {

        log.info("---START GET ALL COMMENTS BY EVENT ID ENDPOINT---");

        return new ResponseEntity<>(commentPagedResponse(commentService.getAllCommentsByEventId(userId, eventId, sort,
                request), from, size), HttpStatus.OK);
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
                pagedEvents.add(EventMapper.toEventShortDto(event));
            }
            return pagedEvents;
        } else {
            return Collections.emptyList();
        }
    }

    private List<CommentDto> commentPagedResponse(List<Comment> comments, int from, int size) {
        List<CommentDto> pagedComments = new ArrayList<>();
        int totalEvents = comments.size();
        int toIndex = from + size;

        if (from <= totalEvents) {
            if (toIndex > totalEvents) {
                toIndex = totalEvents;
            }
            for (Comment comment : comments.subList(from, toIndex)) {
                pagedComments.add(toCommentDto(comment));
            }
            return pagedComments;
        } else {
            return Collections.emptyList();
        }
    }
}