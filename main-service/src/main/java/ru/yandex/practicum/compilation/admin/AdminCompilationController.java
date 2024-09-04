package ru.yandex.practicum.compilation.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.compilation.dto.CompilationDto;

import static ru.yandex.practicum.compilation.dto.CompilationMapper.toCompilationDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService service;

    @PostMapping
    public ResponseEntity<CompilationDto> postCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("---START POST COMPILATION ENDPOINT---");
        return new ResponseEntity<>(toCompilationDto(service.postCompilation(newCompilationDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable int compId) {
        log.info("---START DELETE COMPILATION BY ID ENDPOINT---");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilationById(@PathVariable int compId,
                                                                @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        log.info("---START UPDATE COMPILATION BY ID ENDPOINT---");
        return new ResponseEntity<>(toCompilationDto(service.updateCompilationById(compId, updateCompilationRequest)),
                HttpStatus.CREATED);
    }

}