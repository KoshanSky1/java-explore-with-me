package ru.yandex.practicum.admin.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.event.EventFullDto;
import ru.yandex.practicum.event.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.event.EventShortDto;
import ru.yandex.practicum.event.UpdateEventUserRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryDto;
import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryFromCategoryDto;
import static ru.yandex.practicum.admin.users.UserMapper.toUserShortDto;

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

    public static Event toEventFromNewEventDto(NewEventDto newEventDto, Category category, User initiator) {
        return new Event(
            null,
            newEventDto.getAnnotation(),
            category,
            LocalDateTime.now(),
            newEventDto.getDescription(),
            newEventDto.getEventDate(),
            initiator,
            newEventDto.getLocation(),
            newEventDto.getPaid(),
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
                event.getAnnotation(),
                toCategoryDto(event.getCategory()),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
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

    public static Event toEventFromUpdateEventUserRequest(UpdateEventUserRequest updateEventUserRequest, int eventId,
                                                          User initiator) {
        return new Event(
                (long) eventId,
                updateEventUserRequest.getAnnotation(),
                toCategoryFromCategoryDto(updateEventUserRequest.getCategory()),
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
                LocalDateTime.now(),
                updateEventAdminRequest.getDescription(),
                updateEventAdminRequest.getEventDate(),
                initiator,
                updateEventAdminRequest.getLocation(),
                updateEventAdminRequest.getPaid(),
                updateEventAdminRequest.getParticipantLimit(),
                LocalDateTime.now(),
                updateEventAdminRequest.getRequestModeration(),
                null,
                updateEventAdminRequest.getTitle(),
                null
        );
    }

    public static List<EventShortDto> toListEventShortDto(List<Event> events) {
        List<EventShortDto> eventsShortDto = new ArrayList<>();

        for (Event e : events) {
            eventsShortDto.add(toEventShortDto(e));
        }

        return eventsShortDto;
    }




}