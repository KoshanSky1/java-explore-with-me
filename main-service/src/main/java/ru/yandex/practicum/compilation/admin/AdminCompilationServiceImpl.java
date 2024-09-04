package ru.yandex.practicum.compilation.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.compilation.repository.CompilationRepository;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.compilation.model.Compilation;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.event.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.compilation.dto.CompilationMapper.toCompilationFromNewCompilationDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    @Override
    public Compilation postCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = new ArrayList<>();
        Set<Long> eventsIds = newCompilationDto.getEvents();

        for (Long i : newCompilationDto.getEvents()) {
            events.add(eventRepository.findById((i)).orElseThrow());
        }
        Compilation compilation = toCompilationFromNewCompilationDto(newCompilationDto, events);

        try {
            return repository.save(compilation);
        } catch (ConflictException e) {
            throw new ConflictException(",,,");
        }
    }

    @Override
    public void deleteCompilation(int compId) {
        repository.deleteById((long) compId);
    }

    @Override
    public Compilation updateCompilationById(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = repository.findById((long) compId).orElseThrow();
        List<Event> events = new ArrayList<>();
        Set<Integer> eventsIds = updateCompilationRequest.getEvents();

        for (Integer i : updateCompilationRequest.getEvents()) {
            events.add(eventRepository.findById(Long.valueOf(i)).orElseThrow());
        }

        if (!events.isEmpty()) {
            compilation.setEvents(events);
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
            compilation.setTitle(updateCompilationRequest.getTitle());

        return repository.save(compilation);
    }
}