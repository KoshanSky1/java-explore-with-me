package ru.yandex.practicum.category.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;

import static ru.yandex.practicum.category.dto.CategoryMapper.toCategoryDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {

        log.info("---START ADD CATEGORY ENDPOINT---");

        return new ResponseEntity<>(toCategoryDto(service.addCategory(newCategoryDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable int catId) {

        log.info("---START DELETE CATEGORY BY ID ENDPOINT---");

        service.deleteCategoryById(catId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable int catId,
                                                          @RequestBody NewCategoryDto newCategoryDto) {

        log.info("---START UPDATE CATEGORY BY ID ENDPOINT---");

        return new ResponseEntity<>(toCategoryDto(service.updateCategoryById(catId, newCategoryDto)), HttpStatus.OK);
    }

}