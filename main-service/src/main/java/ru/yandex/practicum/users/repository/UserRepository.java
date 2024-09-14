package ru.yandex.practicum.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.users.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIdIn(List<Long> ids);

}