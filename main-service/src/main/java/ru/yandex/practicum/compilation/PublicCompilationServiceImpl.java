package ru.yandex.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final PublicCompilationRepository repository;

    @Override
    public List<Compilation> getCompilations(boolean pinned) {
        return repository.findAllByPinned(pinned);
    }

    @Override
    public Optional<Compilation> getCompilationById(long compId) {
        return repository.findById(compId);
    }

}