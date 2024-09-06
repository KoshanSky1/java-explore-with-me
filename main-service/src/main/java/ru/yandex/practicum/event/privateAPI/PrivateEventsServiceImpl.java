package ru.yandex.practicum.event.privateAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.repository.CategoryRepository;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.error.NotFoundException;
import ru.yandex.practicum.event.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.Location;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.enums.EventStatus;
import ru.yandex.practicum.event.model.enums.StateAction;
import ru.yandex.practicum.event.repository.EventRepository;
import ru.yandex.practicum.event.repository.LocationRepository;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.request.repository.RequestRepository;
import ru.yandex.practicum.users.model.User;
import ru.yandex.practicum.users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static ru.yandex.practicum.event.dto.EventMapper.toEventFromNewEventDto;
import static ru.yandex.practicum.event.dto.EventMapper.toLocation;
import static ru.yandex.practicum.event.model.enums.EventStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrivateEventsServiceImpl implements PrivateEventsService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<Event> getEventsByUser(int userId) {
       return eventRepository.findAllByInitiatorId(userId);
    }

    @Override
    public Event addEvent(int userId, NewEventDto newEventDto) {
        Event event = new Event();

        if (newEventDto.getPaid() == null) {
            newEventDto.setPaid(false);
        }
        if (newEventDto.getParticipantLimit() == null) {
            newEventDto.setParticipantLimit(0);
        }
        if (newEventDto.getRequestModeration() == null) {
            newEventDto.setRequestModeration(true);
        }

        //System.out.println(newEventDto.getCategory());
        //System.out.println(categoryRepository.findAll());
        Category category = categoryRepository.findById(Long.valueOf(newEventDto.getCategory())).orElseThrow();

        User user = userRepository.findById((long) userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id=%d was not found", userId)));
        Location location = locationRepository.save(toLocation(newEventDto.getLocation()));
        //event.setConfirmedRequests(0);
        try {
            event = eventRepository.save(toEventFromNewEventDto(newEventDto, category, user, location));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Field: category. Error: must not be blank. Value: null");
        }
        return event;
    }

    @Override
    public Event getEventById(int userId, int eventId) {
        Event event = eventRepository.findById((long) eventId).orElseThrow(() -> new NotFoundException(
                String.format("Event not found with id = %s and userId = %s", eventId, userId)));
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(String.format("Event not found with id = %s and userId = %s", eventId, userId));
        }
        return event;
    }

    @Override
    public Event updateEventById(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
       // System.out.println(updateEventUserRequest);
        Event event = eventRepository.findById((long) eventId).orElseThrow(() -> new NotFoundException(
                String.format("Event not found with id = %s and userId = %s", eventId, userId)));
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(String.format("Event not found with id = %s and userId = %s", eventId, userId));
        }

        if (updateEventUserRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById((long) updateEventUserRequest.getCategory())
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found",
                            updateEventUserRequest.getCategory()))));
        }

        if (event.getState().equals(EventState.PUBLISHED)) {
            System.out.println("hi");
            throw new ConflictException("Event must not be published");
        }

        System.out.println(updateEventUserRequest.getStateAction());
        if (StateAction.CANCEL_REVIEW.toString().equals(updateEventUserRequest.getStateAction().toString())) {
            event.setState(EventState.CANCELED);
        } else if (StateAction.SEND_TO_REVIEW.toString().equals(updateEventUserRequest.getStateAction().toString())) {
            event.setState(EventState.PENDING);
        }
        System.out.println(event);
        return event;
    }

    @Override
    public List<Request> getRequests(int userId, int eventId) {
        //System.out.println(eventId);
        List<Request> allRequests = requestRepository.findAll();
        System.out.println(allRequests);
        System.out.println(userId);
        System.out.println(eventId);
        List<Request> requests = new ArrayList<>();

        for (Request r : allRequests) {
            System.out.println(r.getEvent().getId());
            System.out.println(r.getEvent().getInitiator().getId());
            if (r.getEvent().getId() == eventId && r.getEvent().getInitiator().getId() == userId) {
                requests.add(r);
            }
        }
        //s
        //List<Request> requests = requestRepository.findAllByEventId(eventId);
        //for (Request request : requests) {
        //    if (request.getRequester().getId() != userId) {
        //        requests.remove(request);
        //    }
        //}
        if (requests.isEmpty()) {
            throw new NotFoundException("Event not found with id = " + eventId + " and userId " + userId);
        }
        return requests;
    }

    @Override
    public List<Request> updateRequests(int userId, int eventId,
                                  EventRequestStatusUpdateRequest request) {

        List<Integer> requestIds = request.getRequestIds();
        EventStatus status = getEventStatus(request.getStatus());

        Event event = eventRepository.findById((long) eventId).orElseThrow(() ->
                new NotFoundException("Event not found with id = " + eventId + " and userId " + userId));
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException("Event not found with id = " + eventId + " and userId " + userId);
        }
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return List.of();
        }
        final int freeRequests;
        if (event.getConfirmedRequests() == null) {
            freeRequests = 0;
            event.setConfirmedRequests(0);
        } else {
            freeRequests = event.getParticipantLimit() - event.getConfirmedRequests();
        }

        //if (freeRequests <= 0) {
        //    throw new ConflictException(String.format("Event with id=%d has reached participant limit", eventId));
        //}
        List<Request> requests = requestRepository.findAllByIdInAndStatus(requestIds, PENDING);

        AtomicInteger freeRequestsLeft = new AtomicInteger(freeRequests);
        AtomicLong confirmedRequests = new AtomicLong(event.getConfirmedRequests());
        requests.forEach(rq -> {
            if (freeRequestsLeft.getAndDecrement() > 0) {
                rq.setStatus(status);
                confirmedRequests.incrementAndGet();
            } else {
                rq.setStatus(REJECTED);
            }
        });
        event.setConfirmedRequests((int) confirmedRequests.get());

        return requests;
    }

}