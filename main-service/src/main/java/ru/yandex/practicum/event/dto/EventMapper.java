package ru.yandex.practicum.event.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.Location;
import ru.yandex.practicum.event.model.search.SearchEventsArgs;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.category.dto.CategoryMapper.toCategoryDto;
import static ru.yandex.practicum.users.dto.UserMapper.toUserShortDto;

@Component
@RequiredArgsConstructor
public class EventMapper {

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                toCategoryDto(event.getCategory()),
                null,
                event.getEventDate(),
                toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static Event toEventFromNewEventDto(NewEventDto newEventDto, Category category, User initiator,
                                               Location location) {
        return new Event(
                null,
                newEventDto.getAnnotation(),
                category,
                null,
                LocalDateTime.now(),
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                initiator,
                location,
                newEventDto.getPaid(),
                //null,
                newEventDto.getParticipantLimit(),
                LocalDateTime.now(),
                newEventDto.getRequestModeration(),
                null,
                newEventDto.getTitle(),
                null
        );
    }

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                toCategoryDto(event.getCategory()),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static Event toEventFromUpdateEventUserRequest(UpdateEventUserRequest updateEventUserRequest, int eventId,
                                                          User initiator, Category category) {
        return new Event(
                (long) eventId,
                updateEventUserRequest.getAnnotation(),
                category,
                null,
                LocalDateTime.now(),
                updateEventUserRequest.getDescription(),
                updateEventUserRequest.getEventDate(),
                initiator,
                updateEventUserRequest.getLocation(),
                updateEventUserRequest.getPaid(),
                updateEventUserRequest.getParticipantLimit(),
                LocalDateTime.now(),
                updateEventUserRequest.getRequestModeration(),
                null,
                updateEventUserRequest.getTitle(),
                null
        );
    }

    public static Event toEventFromUpdateEventAdminRequest(UpdateEventAdminRequest updateEventAdminRequest, int eventId,
                                                           User initiator, Category category) {
        return new Event(
                (long) eventId,
                updateEventAdminRequest.getAnnotation(),
                category,
                null,
                LocalDateTime.now(),
                updateEventAdminRequest.getDescription(),
                updateEventAdminRequest.getEventDate(),
                initiator,
                toLocation(updateEventAdminRequest.getLocation()),
                updateEventAdminRequest.getPaid(),
                updateEventAdminRequest.getParticipantLimit(),
                LocalDateTime.now(),
                updateEventAdminRequest.getRequestModeration(),
                null,
                updateEventAdminRequest.getTitle(),
                null
        );
    }

    public static List<EventShortDto> toListEventShortDtoFromListEvents(List<Event> events) {
        List<EventShortDto> eventsShortDto = new ArrayList<>();

        for (Event e : events) {
            eventsShortDto.add(toEventShortDto(e));
        }

        return eventsShortDto;
    }

    public static List<EventShortDto> toListEventShortDtoFromSetEvents(Set<Event> events) {
        List<EventShortDto> eventsShortDto = new ArrayList<>();

        for (Event e : events) {
            eventsShortDto.add(toEventShortDto(e));
        }

        return eventsShortDto;
    }

    public static EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<ParticipationRequestDto> confirmedRequests,
    List<ParticipationRequestDto> rejectedRequests) {

        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    public static Location toLocation(LocationDto locationDto) {
        return new Location(
                null,
                locationDto.getLat(),
                locationDto.getLon()
        );
    }

    public static LocationDto toLocation(Location location) {
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }

    public static SearchEventsArgs toSearchEventsArgs(LocalDateTime rangeStart, LocalDateTime rangeEnd, String text,
                                                      List<Long> categories, Boolean paid, Boolean onlyAvailable,
                                                      String sort, int from, int size, HttpServletRequest request) {
        return new SearchEventsArgs(
                text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size,
                request
        );



}






}