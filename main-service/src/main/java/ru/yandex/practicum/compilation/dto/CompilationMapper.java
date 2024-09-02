package ru.yandex.practicum.compilation.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.admin.compilation.NewCompilationDto;
import ru.yandex.practicum.admin.compilation.UpdateCompilationRequest;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.compilation.Compilation;

import java.util.List;

import static ru.yandex.practicum.admin.event.EventMapper.toListEventShortDto;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                toListEventShortDto(compilation.getEvents())
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
                                                                        updateCompilationRequest,
                                                                        List<Event> events) {
        return new Compilation(
                null,
                updateCompilationRequest.getTitle(),
                updateCompilationRequest.getPinned(),
                events
        );
    }

}