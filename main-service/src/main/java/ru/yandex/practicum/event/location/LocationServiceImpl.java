package ru.yandex.practicum.event.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.event.location.LocationService;
import ru.yandex.practicum.event.model.Location;
import ru.yandex.practicum.event.repository.LocationRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;

    @Override
    public void saveLocation(Location location) {
        repository.save(location);
    }
}