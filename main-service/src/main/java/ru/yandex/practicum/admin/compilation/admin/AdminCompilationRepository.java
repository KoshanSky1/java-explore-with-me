package ru.yandex.practicum.admin.compilation.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.compilation.Compilation;

public interface AdminCompilationRepository extends JpaRepository<Compilation, Long> {

}