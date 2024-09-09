package ru.yandex.practicum.compilation.publicApi;

import ru.yandex.practicum.compilation.model.Compilation;

import java.util.List;

public interface PublicCompilationService {
    List<Compilation> getCompilations(Boolean pinned);

    Compilation getCompilationById(long compId);
}