package ru.yandex.practicum.admin.users.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.users.NewUserRequest;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.error.ConflictException;

import java.util.List;

import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryFromCategoryDto;
import static ru.yandex.practicum.admin.users.UserMapper.toUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository repository;

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
                    +  "could not execute statement");
        }
        return user;
    }

    @Override
    public void deleteUserById(int userId) {
        repository.deleteById((long) userId);
    }
}