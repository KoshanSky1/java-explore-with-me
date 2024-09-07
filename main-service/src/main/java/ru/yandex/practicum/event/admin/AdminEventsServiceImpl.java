package ru.yandex.practicum.event.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.event.dto.UpdateEventAdminRequest;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.enums.EventStateAction;
import ru.yandex.practicum.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventRepository eventsRepository;

    @Override
    public List<Event> getEvents(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd) {

        if (states == null) {
            return eventsRepository.findEventsWithoutStates(users, categories, rangeStart, rangeEnd);
        } else {
            List<EventState> eventStates = findByState(states);
            return eventsRepository.findEvents(users, eventStates, categories, rangeStart, rangeEnd);
        }
    }

    @Override
    public Event updateEventById(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventsRepository.findById((long) eventId).orElseThrow();

        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Cannot publish the event because it's not in the right state: PUBLISHED");
        } else if (event.getState().equals(EventState.CANCELED)) {
            throw new ConflictException("Cannot publish the event because it's not in the right state: CANCELED");
        } else {
            if (updateEventAdminRequest.getStateAction().toString().equals(EventStateAction.PUBLISH_EVENT.toString())) {
                event.setState(EventState.PUBLISHED);
            }
            if (updateEventAdminRequest.getStateAction().toString().equals(EventStateAction.REJECT_EVENT.toString())) {
                event.setState(EventState.CANCELED);
            }
        }

        return eventsRepository.save(event);
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