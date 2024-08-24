package ru.yandex.practicum.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.compilation.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {


}