package ru.yandex.practicum.admin.event.privateAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.category.admin.AdminCategoryRepository;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.NewEventDto;
import ru.yandex.practicum.admin.users.admin.AdminUserRepository;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.event.*;
import ru.yandex.practicum.request.Request;
import ru.yandex.practicum.request.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.admin.event.EventMapper.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrivateEventsServiceImpl implements PrivateEventsService {
    private final PrivateEventsRepository eventsRepository;
    private final AdminUserRepository userRepository;
    private final AdminCategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<Event> getEventsByUser(int userId) {
       return eventsRepository.findAllByInitiatorId(userId);
    }

    @Override
    public Event addEvent(int userId, NewEventDto newEventDto) {
        Category category = categoryRepository.findById(Long.valueOf(newEventDto.getCategoryId())).orElseThrow();
        User user = userRepository.findById((long) userId).orElseThrow();
        return eventsRepository.save(toEventFromNewEventDto(newEventDto, category, user));
    }

    @Override
    public Optional<Event> getEventById(int userId, int eventId) {
        return eventsRepository.findById((long) eventId);
    }

    @Override
    public Event updateEventById(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        User user = userRepository.findById((long) userId).orElseThrow();
        return eventsRepository.save(toEventFromUpdateEventUserRequest(updateEventUserRequest, eventId, user));
    }

    @Override
    public List<Request> getRequests(int userId, int eventId) {
        return requestRepository.findAllByEventId(eventId);
    }

    @Override
    public List<Request> updateRequests(int userId, int eventId,
                                  EventRequestStatusUpdateRequest request) {

        List<Integer> requestIds = request.getRequestIds();
        List<Request> requestsUpdated = new ArrayList<>();

        for (Integer i : requestIds) {
            Request requestUpdated = requestRepository.findById(Long.valueOf(i)).orElseThrow();
            requestUpdated.setStatus(request.getStatus());
            requestsUpdated.add(requestRepository.save(requestUpdated));
        }
        return requestsUpdated;
    }


}