package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.client.StatsClient;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatsClient statsClient;
    //private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end,
                                           @RequestParam List<String> uris, Boolean unique) {
        log.info("---START GET STATS ENDPOINT---");
        return statsClient.getStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> postStats(EndpointHit endpointHit) {
        log.info("---START POST STATS ENDPOINT---");
        return statsClient.postStats(endpointHit);
    }

}