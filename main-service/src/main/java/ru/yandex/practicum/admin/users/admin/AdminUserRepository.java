package ru.yandex.practicum.admin.users.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.admin.users.User;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIdIn(List<Long> ids);

}