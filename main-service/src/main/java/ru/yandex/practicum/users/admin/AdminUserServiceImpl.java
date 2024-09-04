package ru.yandex.practicum.users.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.users.dto.NewUserRequest;
import ru.yandex.practicum.users.model.User;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.users.repository.UserRepository;

import java.util.List;

import static ru.yandex.practicum.users.dto.UserMapper.toUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository repository;

    @Override
    public List<User> getUsers(List<Long> ids) {
        if (ids == null) {
            return repository.findAll();
        } else {
            return repository.findAllByIdIn(ids);
        }
    }

    @Override
    public User postUser(NewUserRequest newUserRequest) {
        User user;
        try {
            user = repository.save(toUser(newUserRequest));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_email]; "
                    + "nested exception is org.hibernate.exception.ConstraintViolationException: "
                    + "could not execute statement");
        }
        return user;
    }

    @Override
    public void deleteUserById(int userId) {
        repository.deleteById((long) userId);
    }
}