package ru.yandex.practicum.admin.users.privateApi;

import ru.yandex.practicum.admin.event.privateAPI.ParticipationRequestDto;
import ru.yandex.practicum.admin.users.NewUserRequest;
import ru.yandex.practicum.request.Request;
import ru.yandex.practicum.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface PrivateUsersService {
    List<Request> getRequestsByUserId(int userId);

    Request postRequestsByUserId(int userId, int eventId);

    Request updateRequestsByUserId(int userId, int requestId);

}
