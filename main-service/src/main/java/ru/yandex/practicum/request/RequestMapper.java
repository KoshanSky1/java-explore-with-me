package ru.yandex.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.EventStatus;
import ru.yandex.practicum.admin.users.NewUserRequest;
import ru.yandex.practicum.admin.event.privateAPI.ParticipationRequestDto;
import ru.yandex.practicum.admin.users.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    public static UserRequest toUserRequest(NewUserRequest newUserRequest) {
        return new UserRequest(
            null,
            newUserRequest.getName(),
            newUserRequest.getEmail()
        );
    }

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreatedOn(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                String.valueOf(request.getStatus())
        );
    }

    public static Request toRequest(User user, Event event) {
        return new Request(
                null,
                event,
                user,
                LocalDateTime.now(),
                EventStatus.CONFIRMED
        );
    }

}