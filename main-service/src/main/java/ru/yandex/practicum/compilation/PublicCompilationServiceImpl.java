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
    public List<Compilation> getCompilations(Boolean pinned) {
        if (pinned != null) {
            return repository.findAllByPinned(pinned);
        } else {
            return repository.findAll();
        }
    }

    @Override
    public Optional<Compilation> getCompilationById(long compId) {
        return repository.findById(compId);
    }

}