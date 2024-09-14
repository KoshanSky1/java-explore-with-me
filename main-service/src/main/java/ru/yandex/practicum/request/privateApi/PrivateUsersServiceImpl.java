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

        log.info("Получен список запросов для пользователя с id=" + userId);

        return requests;
    }

    @Override
    public Request postRequestsByUserId(int userId, int eventId) {
        Request request;

        Event event = eventsRepository.findById((long) eventId).orElseThrow(()
                -> new NotFoundException("Event not found with id = " + eventId));

        User user = usersRepository.findById((long) userId).orElseThrow(()
                -> new NotFoundException("User not found with id = " + userId));

        Integer confirmedRequests = event.getConfirmedRequests();

        if (repository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException("Request with requesterId= " + userId + " and eventId=" + eventId + " already exist");
        }
        if (event.getInitiator().getId() == userId) {
            throw new ConflictException("User with id=" + userId + " must not be equal to initiator");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Event with id=" + eventId + " is not published");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConflictException("Event with id=" + eventId + " has reached participant limit");
        }
        if (!event.getRequestModeration()) {
            if (confirmedRequests == null) {
                confirmedRequests = 0;
            }
            event.setConfirmedRequests(confirmedRequests + 1);
            event = eventsRepository.save(event);
        }
        request = repository.save(toRequest(user, event));

        if (event.getParticipantLimit() == 0) {
            request.setStatus(EventStatus.CONFIRMED);
        }
        log.info("Добавлен запрос для пользователя с id=" + userId + " и события с id=" + eventId);

        return repository.save(request);
    }

    @Override
    public Request updateRequestsByUserId(int userId, int requestId) {
        Request request = repository.findByIdAndRequesterId(requestId, userId);

        if (request == null) {
            throw new ConflictException("Request with id= " + requestId + " and requesterId= "
                    + userId + " was not found");
        }
        log.info("Обновлен запрос с id=" + requestId);

        request.setStatus(EventStatus.CANCELED);

        return repository.save(request);
    }
}