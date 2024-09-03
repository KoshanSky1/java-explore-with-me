package ru.yandex.practicum.admin.compilation.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.compilation.NewCompilationDto;
import ru.yandex.practicum.admin.compilation.UpdateCompilationRequest;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.privateAPI.EventRepository;
import ru.yandex.practicum.compilation.Compilation;
import ru.yandex.practicum.event.publicAPI.PublicEventsRepository;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.compilation.dto.CompilationMapper.toCompilationFromNewCompilationDto;
import static ru.yandex.practicum.compilation.dto.CompilationMapper.toCompilationFromUpdateCompilationRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository repository;
    private final EventRepository eventsRepository;

    @Override
    public Compilation postCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = new ArrayList<>();
        for (Integer i : newCompilationDto.getEvents()) {
            System.out.println(newCompilationDto.getEvents());
            events.add(eventsRepository.findById(Long.valueOf(i)).orElseThrow());
        }
        System.out.println(events);
        return repository.save(toCompilationFromNewCompilationDto(newCompilationDto, events));
    }

    @Override
    public void deleteCompilation(int compId) {
        repository.deleteById((long) compId);
    }

    @Override
    public Compilation updateCompilationById(int compId, UpdateCompilationRequest updateCompilationRequest) {
        List<Event> events = new ArrayList<>();
        for (Integer i : updateCompilationRequest.getEvents()) {
            events.add(eventsRepository.findById(Long.valueOf(i)).orElseThrow());
        }
        System.out.println(events);
        return repository.save(toCompilationFromUpdateCompilationRequest(updateCompilationRequest, events));
    }
}