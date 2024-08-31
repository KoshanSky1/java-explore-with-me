package ru.yandex.practicum.admin.users.privateApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.admin.users.admin.AdminUserRepository;
import ru.yandex.practicum.event.publicAPI.PublicEventsRepository;
import ru.yandex.practicum.request.Request;

import java.util.List;

import static ru.yandex.practicum.request.RequestMapper.toRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrivateUsersServiceImpl implements PrivateUsersService {
    private final PrivateUsersRepository repository;
    private final PublicEventsRepository eventsRepository;
    private final AdminUserRepository usersRepository;

    @Override
    public List<Request> getRequestsByUserId(int userId) {
        return repository.findAllByRequesterId(userId);
    }

    @Override
    public Request postRequestsByUserId(int userId, int eventId) {
        Event event = eventsRepository.findById((long) eventId).orElseThrow();
        User user = usersRepository.findById((long) userId).orElseThrow();
        return repository.save(toRequest(user, event));
    }

    @Override
    public Request updateRequestsByUserId(int userId, int requestId) {
        Request request = repository.findByIdAndRequesterId(requestId, userId);
        return repository.save(request);
    }
}