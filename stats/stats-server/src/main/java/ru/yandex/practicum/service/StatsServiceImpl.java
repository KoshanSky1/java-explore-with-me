package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.StatsRepository;
import ru.yandex.practicum.exception.StatsValidationException;
import ru.yandex.practicum.model.EndpointHit;
import ru.yandex.practicum.model.ViewStats;
import ru.yandex.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;
import static ru.yandex.practicum.StatsMapper.toEndpointHitDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Override
    public EndpointHitDto postStats(EndpointHit endpointHit) {
        log.info(format("Записан новый элемент статистики: %s", endpointHit));
        repository.save(endpointHit);
        return toEndpointHitDto(endpointHit);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (checkDate(start, end)) {
            throw new StatsValidationException("Даты начала и конца указаны неверно");
        }
        if (unique) {
            log.info("Cформирована статистика с уникальными посещениями");
            return pagedResponse(repository.getUniqueStats(start, end, uris), 0, 20);
        } else {
            log.info("Cформирована статистика");
            return pagedResponse(repository.getStats(start, end, uris), 0, 20);
        }
    }

    private List<ViewStats> pagedResponse(List<ViewStats> views, int from, int size) {
        int totalViews = views.size();
        int toIndex = from + size;

        if (from <= totalViews) {
            if (toIndex > totalViews) {
                toIndex = totalViews;
            }
            return views.subList(from, toIndex);
        } else {
            return Collections.emptyList();
        }
    }

    private Boolean checkDate(LocalDateTime start, LocalDateTime end) {
        return !end.isBefore(LocalDateTime.now()) && !start.isBefore(LocalDateTime.now()) && !end.equals(start)
                && !start.isAfter(end);
    }


}