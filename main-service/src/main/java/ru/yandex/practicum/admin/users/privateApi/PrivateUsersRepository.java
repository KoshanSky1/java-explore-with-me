package ru.yandex.practicum.admin.users.privateApi;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.request.Request;

import java.util.List;

public interface PrivateUsersRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(int userId);

    Request findByIdAndRequesterId(int requestId, int userId);
}