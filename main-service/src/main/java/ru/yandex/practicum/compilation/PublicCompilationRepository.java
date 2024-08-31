package ru.yandex.practicum.compilation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicCompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByPinned(Boolean pinned);
}