package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping(path = "/bookings")
public class StatsController {
    private final StatsService statsService;
    //private final BookingMapper bookingMapper;
    //private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    @PostMapping("/hit")
    public ResponseEntity<EndpointHit> postStat(@RequestBody EndpointHit endpointHit) {
        log.info("---START POST STAT ENDPOINT---");
        return new ResponseEntity<>(statsService.postStats(endpointHit), HttpStatus.CREATED);

    }

    @GetMapping("/stats")
    public ResponseEntity<List<VievStats>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris,
                                                           Boolean unique) {
        log.info("---START GET STATS ENDPOINT---");
        return new ResponseEntity<>(statsService.getStats(start, end, uris, unique), HttpStatus.OK);
    }

}