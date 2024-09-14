package ru.yandex.practicum.event.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    @Override
    public void saveLocation(Location location) {
        log.info("Добавлена новая локация - широта: " + location.getLat() + ", долгота: " + location.getLon());

        repository.save(location);
    }
}