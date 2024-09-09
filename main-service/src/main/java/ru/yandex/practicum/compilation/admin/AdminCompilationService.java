package ru.yandex.practicum.compilation.admin;

import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.compilation.model.Compilation;

public interface AdminCompilationService {
    Compilation postCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(int compId);

    Compilation updateCompilationById(int compId, UpdateCompilationRequest updateCompilationRequest);
}