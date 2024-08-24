package ru.yandex.practicum.compilation;

import ru.yandex.practicum.compilation.Compilation;

import java.util.List;
import java.util.Optional;

public interface CompilationService {

    List<Compilation> getCompilations(boolean pinned);

    Optional<Compilation> getCompilationById(long compId);
}