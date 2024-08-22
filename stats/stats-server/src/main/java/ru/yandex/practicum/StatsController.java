package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.ViewStats;
import ru.yandex.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.yandex.practicum.StatsMapper.toEndpointHit;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> postStat(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("---START POST STAT ELEMENT ENDPOINT---");
        return new ResponseEntity<>(statsService.postStats(toEndpointHit(endpointHitDto)), HttpStatus.CREATED);

    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                    LocalDateTime start,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                    LocalDateTime end,
                                                    @RequestParam(required = false) List<String> uris,
                                                    @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("---START GET STATS ENDPOINT---");
        return new ResponseEntity<>(statsService.getStats(start, end, uris, unique), HttpStatus.OK);
    }

}