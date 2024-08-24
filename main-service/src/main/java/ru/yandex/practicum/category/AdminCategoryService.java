package ru.yandex.practicum.category;

import ru.yandex.practicum.category.dto.CategoryDto;

public interface AdminCategoryService {

    Category addCategory(CategoryDto categoryDto);

    void deleteCategoryById(long catId);

    Category updateCategoryById(long catId, CategoryDto categoryDto);
}