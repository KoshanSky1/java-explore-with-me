package ru.yandex.practicum.event.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.error.IncorrectParameterException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.Location;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.search.SearchEventsArgs;
import ru.yandex.practicum.event.model.search.SearchPublicEventsArgs;
import ru.yandex.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                event.getDate(),
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
                0,
                LocalDateTime.now(),
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                initiator,
                location,
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                LocalDateTime.now(),
                newEventDto.getRequestModeration(),
                EventState.PENDING,
                newEventDto.getTitle(),
                0
        );
    }

    public static EventFullDto toEventFullDto(Event event) {

        return new EventFullDto(
                event.getAnnotation(),
                toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getDate(),
                event.getId(),
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

    public static List<EventShortDto> toListEventShortDtoFromListEvents(List<Event> events) {
        List<EventShortDto> eventsShortDto = new ArrayList<>();

        for (Event e : events) {
            eventsShortDto.add(toEventShortDto(e));
        }

        return eventsShortDto;
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
                                                      String sort, HttpServletRequest request) {

        if (rangeEnd != null && rangeEnd.isBefore(LocalDateTime.now())) {
            throw new IncorrectParameterException("Event must be published");
        }

        return new SearchEventsArgs(
                text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                request
        );
    }

    public static SearchPublicEventsArgs toSearchPublicEventsArgs(List<Long> users, List<String> states,
                                                                  List<Long> categories, LocalDateTime rangeStart,
                                                                  LocalDateTime rangeEnd) {

        return new SearchPublicEventsArgs(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd
        );
    }
}