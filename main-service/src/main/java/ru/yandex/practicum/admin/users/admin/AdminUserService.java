package ru.yandex.practicum.admin.users.admin;

import ru.yandex.practicum.admin.users.NewUserRequest;
import ru.yandex.practicum.admin.users.User;

import java.util.List;

public interface AdminUserService {
    List<User> getUsers(List<Long> ids);

    User postUser(NewUserRequest newUserRequest);

    void deleteUserById(int userId);
}