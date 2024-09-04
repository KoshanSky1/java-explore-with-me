package ru.yandex.practicum.users.admin;

import ru.yandex.practicum.users.dto.NewUserRequest;
import ru.yandex.practicum.users.model.User;

import java.util.List;

public interface AdminUserService {
    List<User> getUsers(List<Long> ids);

    User postUser(NewUserRequest newUserRequest);

    void deleteUserById(int userId);
}