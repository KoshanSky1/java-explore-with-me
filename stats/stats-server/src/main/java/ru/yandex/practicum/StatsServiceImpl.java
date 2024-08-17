package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final StatsService statsService;

    @Override
    public EndpointHit postStats(EndpointHit endpointHit) {
        log.info(format("Записан новый элемент статистики: %s", endpointHit));
        return repository.save(endpointHit);
    }

    @Override
    public List<VievStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return repository.getUniqueStat(start, end, uris);
        } else {
            return repository.getStat(start, end, uris);
        }
    }
}