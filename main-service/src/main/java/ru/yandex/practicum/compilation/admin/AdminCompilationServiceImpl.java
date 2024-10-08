package ru.yandex.practicum.compilation.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.compilation.repository.CompilationRepository;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.error.NotFoundException;
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

        if (newCompilationDto.getEvents() != null) {
            events.addAll(eventRepository.findAllByIdIn(eventsIds));
        }

        Compilation compilation = toCompilationFromNewCompilationDto(newCompilationDto, events);

        try {
            log.info("Добавлена новая подборка: " + compilation);

            return repository.save(compilation);
        } catch (ConflictException e) {
            throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_compilation_name];"
                    + " nested exception is org.hibernate.exception.ConstraintViolationException:"
                    + " could not execute statement");
        }

    }

    @Override
    public void deleteCompilation(int compId) {
        log.info("Удалена подборка с id=" + compId);

        repository.deleteById((long) compId);
    }

    @Override
    public Compilation updateCompilationById(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = repository.findById((long) compId).orElseThrow(()
                -> new NotFoundException("Compilation not found with id=" + compId));

        List<Event> events = new ArrayList<>();
        Set<Long> eventsIds = updateCompilationRequest.getEvents();

        if (updateCompilationRequest.getEvents() != null) {
            events.addAll(eventRepository.findAllByIdIn(eventsIds));
        }

        if (!events.isEmpty()) {
            compilation.setEvents(events);
        }

        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }

        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }

        log.info("Обновлена с подборка id=" + compId);

        return repository.save(compilation);
    }

}