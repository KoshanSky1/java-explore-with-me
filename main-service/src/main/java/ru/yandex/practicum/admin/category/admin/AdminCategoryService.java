package ru.yandex.practicum.admin.category.admin;

import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.category.dto.CategoryDto;
import ru.yandex.practicum.admin.category.dto.NewCategoryDto;

public interface AdminCategoryService {
    Category addCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(long catId);

    Category updateCategoryById(long catId, CategoryDto categoryDto);
}