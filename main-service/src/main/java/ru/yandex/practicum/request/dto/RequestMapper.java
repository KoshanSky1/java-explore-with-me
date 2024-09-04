package ru.yandex.practicum.request.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.users.dto.NewUserRequest;
import ru.yandex.practicum.users.model.User;

import java.time.LocalDateTime;

import static ru.yandex.practicum.event.model.enums.EventStatus.CONFIRMED;
import static ru.yandex.practicum.event.model.enums.EventStatus.PENDING;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    //public static UserRequest toUserRequest(NewUserRequest newUserRequest) {
    //    return new UserRequest(
    //        null,
    //        newUserRequest.getName(),
     //       newUserRequest.getEmail()
     //   );
    //}

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreatedOn(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }

    public static Request toRequest(User user, Event event) {
        return new Request(
                null,
                event,
                user,
                LocalDateTime.now(),
                event.getRequestModeration() ? PENDING : CONFIRMED
        );
    }

}