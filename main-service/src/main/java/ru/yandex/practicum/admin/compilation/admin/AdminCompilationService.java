package ru.yandex.practicum.admin.compilation.admin;

import ru.yandex.practicum.admin.compilation.NewCompilationDto;
import ru.yandex.practicum.admin.compilation.UpdateCompilationRequest;
import ru.yandex.practicum.compilation.Compilation;

public interface AdminCompilationService {
    Compilation postCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(int compId);

    Compilation updateCompilationById(int compId, UpdateCompilationRequest updateCompilationRequest);

}
