package ru.yandex.practicum.users.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.users.dto.NewUserRequest;
import ru.yandex.practicum.users.model.User;
import ru.yandex.practicum.users.dto.UserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.users.dto.UserMapper.toUserDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUsersController {
    private final AdminUserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) List<Long> ids,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("---START GET USERS ENDPOINT---");

        return new ResponseEntity<>(pagedResponse(service.getUsers(ids), from, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> postUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("---START POST USER ENDPOINT---");
        UserDto userDto = toUserDto(service.postUser(newUserRequest));

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int userId) {
        log.info("---START DELETE USER BY ID ENDPOINT---");
        service.deleteUserById(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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