package ru.yandex.practicum.request.privateApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.error.NotFoundException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.enums.EventStatus;
import ru.yandex.practicum.event.repository.EventRepository;
import ru.yandex.practicum.request.repository.RequestRepository;
import ru.yandex.practicum.users.model.User;
import ru.yandex.practicum.users.repository.UserRepository;
import ru.yandex.practicum.request.model.Request;

import java.util.List;

import static ru.yandex.practicum.request.dto.RequestMapper.toRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrivateUsersServiceImpl implements PrivateUsersService {
    private final RequestRepository repository;
    private final EventRepository eventsRepository;
    private final UserRepository usersRepository;

    @Override
    public List<Request> getRequestsByUserId(int userId) {
        List<Request> requests = repository.findAllByRequesterId(userId);
        if (requests.isEmpty()) {
            throw new NotFoundException("User not found with id = " + userId);
        }
        return requests;
    }

    @Override
    public Request postRequestsByUserId(int userId, int eventId) {
        Event event = eventsRepository.findById((long) eventId).orElseThrow(()
                -> new NotFoundException("Event not found with id = " + eventId));

        User user = usersRepository.findById((long) userId).orElseThrow(()
                -> new NotFoundException(String.format("User not found with id = " + userId)));

        Integer сonfirmedRequests = event.getConfirmedRequests();

        if (repository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException("Request with requesterId= " +  userId + " and eventId" + eventId + " already exist");
        }
        if (event.getInitiator().getId() == userId) {
            System.out.println(event.getInitiator().getId());
            System.out.println(userId);
            throw new ConflictException(String.format("User with id=%d must not be equal to initiator", userId));
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException(String.format("Event with id=%d is not published", eventId));
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConflictException(String.format("Event with id=%d has reached participant limit", eventId));
        }
        if (!event.getRequestModeration()) {
            if (сonfirmedRequests == null) {
                сonfirmedRequests = 0;
            }
            event.setConfirmedRequests(сonfirmedRequests + 1);
            event = eventsRepository.save(event);
        }

        return repository.save(toRequest(user, event));
    }

    @Override
    public Request updateRequestsByUserId(int userId, int requestId) {
        Request request = repository.findByIdAndRequesterId(requestId, userId);
        if (request == null) {
            throw new ConflictException("Request with id= " + requestId +
                    "and requesterId= " + userId + " was not found");
        }
        request.setStatus(EventStatus.CANCELED);
        return repository.save(request);
    }
}