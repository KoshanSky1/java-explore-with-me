package ru.yandex.practicum.compilation.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.compilation.model.Compilation;

import java.util.List;

import static ru.yandex.practicum.event.dto.EventMapper.toListEventShortDtoFromListEvents;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                toListEventShortDtoFromListEvents(compilation.getEvents())
        );
    }

    public static Compilation toCompilationFromNewCompilationDto(NewCompilationDto newCompilationDto,
                                                                 List<Event> events) {
        return new Compilation(
                null,
                newCompilationDto.getTitle(),
                newCompilationDto.isPinned(),
                events
        );
    }

    public static Compilation toCompilationFromUpdateCompilationRequest(UpdateCompilationRequest
                                                                        updateCompilationRequest, List<Event> events) {
        return new Compilation(
                null,
                updateCompilationRequest.getTitle(),
                updateCompilationRequest.getPinned(),
                events
        );
    }

}