package ru.yandex.practicum.service;

import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.model.EndpointHit;
import ru.yandex.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHitDto postStats(EndpointHit endpointHit);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}