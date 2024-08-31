package ru.yandex.practicum.admin.users.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.admin.users.NewUserRequest;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.admin.users.UserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.admin.users.UserMapper.toUserDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUsersController {
    private final AdminUserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam int[] ids, int from, int size) {
        log.info("---START GET USERS ENDPOINT---");
        return new ResponseEntity<>(pagedResponse(service.getUsers(ids), from, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> postUser(@RequestBody NewUserRequest newUserRequest) {
        log.info("---START POST USER ENDPOINT---");
        return new ResponseEntity<>(toUserDto(service.postUser(newUserRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        log.info("---START DELETE USER BY ID ENDPOINT---");
        service.deleteUserById(userId);
    }

    private List<UserDto> pagedResponse(List<User> users, int from, int size) {
        List<UserDto> pagedUsers = new ArrayList<>();
        int totalUsers = users.size();
        int toIndex = from + size;

        if (from <= totalUsers) {
            if (toIndex > totalUsers) {
                toIndex = totalUsers;
            }
            for (User user : users.subList(from, toIndex)) {
                pagedUsers.add(toUserDto(user));
            }
            return pagedUsers;
        } else {
            return Collections.emptyList();
        }
    }

}