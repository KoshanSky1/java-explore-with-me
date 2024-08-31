package ru.yandex.practicum.admin.compilation.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.admin.compilation.NewCompilationDto;
import ru.yandex.practicum.admin.compilation.UpdateCompilationRequest;
import ru.yandex.practicum.compilation.dto.CompilationDto;

import static ru.yandex.practicum.compilation.dto.CompilationMapper.toCompilationDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService service;

    @PostMapping
    public ResponseEntity<CompilationDto> postCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("---START POST COMPILATION ENDPOINT---");
        return new ResponseEntity<>(toCompilationDto(service.postCompilation(newCompilationDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable int compId) {
        log.info("---START DELETE COMPILATION BY ID ENDPOINT---");
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilationById(@PathVariable int compId,
                                                @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("---START UPDATE COMPILATION BY ID ENDPOINT---");
        return toCompilationDto(service.updateCompilationById(compId, updateCompilationRequest));
    }

}