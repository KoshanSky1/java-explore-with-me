package ru.yandex.practicum.compilation.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.compilation.model.Compilation;
import ru.yandex.practicum.compilation.repository.CompilationRepository;
import ru.yandex.practicum.error.NotFoundException;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository repository;

    @Override
    public List<Compilation> getCompilations(Boolean pinned) {
        log.info("Сформирован список подборок с pinned= " + pinned);

        if (pinned != null) {
            return repository.findAllByPinned(pinned);
        } else {
            return repository.findAll();
        }
    }

    @Override
    public Compilation getCompilationById(long compId) {
        log.info("Поиск подборки с id=" + compId);

        return repository.findById(compId).orElseThrow(() -> new NotFoundException("Compilation not found with id = " +
                compId));
    }

}