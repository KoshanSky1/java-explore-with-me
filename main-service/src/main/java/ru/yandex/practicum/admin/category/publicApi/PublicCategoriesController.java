package ru.yandex.practicum.admin.category.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.category.dto.CategoryDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoriesController {
    public final PublicCategoriesService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam(defaultValue = "0") int from,
                                                           @RequestParam(defaultValue = "10") int size) {
        log.info("---START GET CATEGORIES ENDPOINT---");
        return new ResponseEntity<>(pagedResponse(service.getCategories(), from, size), HttpStatus.OK);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long catId) {
        log.info("---START GET CATEGORY BY ID ENDPOINT---");
        return new ResponseEntity<>(toCategoryDto(service.getCategoryById(catId).orElseThrow()), HttpStatus.OK);
    }

    private List<CategoryDto> pagedResponse(List<Category> categories, int from, int size) {
        List<CategoryDto> pagedCategories = new ArrayList<>();
        int totalCompilations = categories.size();
        int toIndex = from + size;

        if (from <= totalCompilations) {
            if (toIndex > totalCompilations) {
                toIndex = totalCompilations;
            }
            for (Category category : categories.subList(from, toIndex)) {
                pagedCategories.add(toCategoryDto(category));
            }
            return pagedCategories;
        } else {
            return Collections.emptyList();
        }
    }

}
