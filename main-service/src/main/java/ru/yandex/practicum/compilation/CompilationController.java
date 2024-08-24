package ru.yandex.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.compilation.dto.CompilationDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.compilation.dto.CompilationMapper.toCompilationDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getCompilations(@RequestParam Boolean pinned,
                                                                @RequestParam(defaultValue = "0") int from,
                                                                @RequestParam(defaultValue = "10") int size) {
        log.info("---START GET COMPILATIONS ENDPOINT---");
        return new ResponseEntity<>(pagedResponse(compilationService.getCompilations(pinned), from, size),
                HttpStatus.OK);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable int compId) {
        log.info("---START GET COMPILATION BY ID ENDPOINT---");
        return new ResponseEntity<>(toCompilationDto(compilationService.getCompilationById(compId).orElseThrow()),
                HttpStatus.OK);
    }

    private List<CompilationDto> pagedResponse(List<Compilation> compilations, int from, int size) {
        List<CompilationDto> pagedCompilations = new ArrayList<>();
        int totalCompilations = compilations.size();
        int toIndex = from + size;

        if (from <= totalCompilations) {
            if (toIndex > totalCompilations) {
                toIndex = totalCompilations;
            }
            for (Compilation compilation : compilations.subList(from, toIndex)) {
                pagedCompilations.add(toCompilationDto(compilation));
            }
            return pagedCompilations;
        } else {
            return Collections.emptyList();
        }
    }

}