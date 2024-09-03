package ru.yandex.practicum.compilation;

import java.util.List;
import java.util.Optional;

public interface PublicCompilationService {

    List<Compilation> getCompilations(Boolean pinned);

    Optional<Compilation> getCompilationById(long compId);
}