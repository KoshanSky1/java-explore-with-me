package ru.yandex.practicum.admin.users.privateApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.admin.event.privateAPI.ParticipationRequestDto;
import ru.yandex.practicum.request.Request;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.request.RequestMapper.toParticipationRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateUsersController {
    private final PrivateUsersService service;

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsByUserId(@PathVariable int userId) {
        log.info("---START GET REQUESTS BY USER ID ENDPOINT---");

        List<ParticipationRequestDto> requestsDto = new ArrayList<>();
        List<Request> requestList = service.getRequestsByUserId(userId);

        for (Request r : requestList) {
            requestsDto.add(toParticipationRequestDto(r));
        }

        return new ResponseEntity<>(requestsDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> postRequestsByUserId(@PathVariable int userId,
                                                                        @RequestParam int eventId) {
        log.info("---START POST REQUEST ENDPOINT---");
        return new ResponseEntity<>(toParticipationRequestDto(service.postRequestsByUserId(userId, eventId)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> updateRequestsByUserId(@PathVariable int userId,
                                                                          @PathVariable int requestId) {
        log.info("---START UPDATE REQUEST BY USER ID ENDPOINT---");
        return new ResponseEntity<>(toParticipationRequestDto(service.updateRequestsByUserId(userId, requestId)),
                HttpStatus.OK);
    }

}