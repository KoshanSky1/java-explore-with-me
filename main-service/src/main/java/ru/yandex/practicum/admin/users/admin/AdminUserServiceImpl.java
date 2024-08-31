package ru.yandex.practicum.admin.users.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.users.NewUserRequest;
import ru.yandex.practicum.admin.users.User;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.admin.users.UserMapper.toUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository repository;

    @Override
    public List<User> getUsers(int[] ids) {
        List<Long> userIds = new ArrayList<>();
        for (Integer id : ids) {
            userIds.add(Long.valueOf(id));
        }
        return repository.findAllByIdIn(userIds);
    }

    @Override
    public User postUser(NewUserRequest newUserRequest) {
        return repository.save(toUser(newUserRequest));
    }

    @Override
    public void deleteUserById(int userId) {
        repository.deleteById((long) userId);
    }
}