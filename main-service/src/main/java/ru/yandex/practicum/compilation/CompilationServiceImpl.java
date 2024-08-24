package ru.yandex.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;

    @Override
    public List<Compilation> getCompilations(boolean pinned) {
        return repository.findAll();
    }

    @Override
    public Optional<Compilation> getCompilationById(long compId) {
        return repository.findById(compId);
    }

}