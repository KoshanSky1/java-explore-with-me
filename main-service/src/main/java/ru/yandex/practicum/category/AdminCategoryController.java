package ru.yandex.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.category.dto.CategoryDto;

import static ru.yandex.practicum.category.dto.CategoryMapper.toCategoryDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        log.info("---START ADD CATEGORY ENDPOINT---");
        return new ResponseEntity<>(toCategoryDto(service.addCategory(categoryDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategoryById(@PathVariable int catId) {
        log.info("---START DELETE CATEGORY BY ID ENDPOINT---");
        service.deleteCategoryById(catId);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable int catId,
                                                          @RequestBody CategoryDto categoryDto) {
        log.info("---START UPDATE CATEGORY BY ID ENDPOINT---");
        return new ResponseEntity<>(toCategoryDto(service.updateCategoryById(catId, categoryDto)), HttpStatus.OK);
    }


}