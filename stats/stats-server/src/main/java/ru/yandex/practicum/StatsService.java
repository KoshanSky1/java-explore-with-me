package ru.yandex.practicum;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHit postStats(EndpointHit endpointHit);

    List<VievStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}