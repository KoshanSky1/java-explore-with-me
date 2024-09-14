package ru.yandex.practicum.request.privateApi;

import ru.yandex.practicum.request.model.Request;

import java.util.List;

public interface PrivateUsersService {
    List<Request> getRequestsByUserId(int userId);

    Request postRequestsByUserId(int userId, int eventId);

    Request updateRequestsByUserId(int userId, int requestId);
}
