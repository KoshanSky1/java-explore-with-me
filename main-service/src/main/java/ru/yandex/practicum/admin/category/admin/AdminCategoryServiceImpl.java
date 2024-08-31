package ru.yandex.practicum.admin.category.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.category.dto.CategoryDto;
import ru.yandex.practicum.admin.category.dto.NewCategoryDto;

import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryFromCategoryDto;
import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryFromNewCategoryDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final AdminCategoryRepository repository;

    @Override
    public Category addCategory(NewCategoryDto newCategoryDto) {
        return repository.save(toCategoryFromNewCategoryDto(newCategoryDto));
    }

    @Override
    public void deleteCategoryById(long catId) {
        repository.deleteById(catId);
    }

    @Override
    public Category updateCategoryById(long catId, CategoryDto categoryDto) {
        return repository.save(toCategoryFromCategoryDto(categoryDto));
    }

}