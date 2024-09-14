package ru.yandex.practicum.category.admin;

import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.dto.NewCategoryDto;

public interface AdminCategoryService {
    Category addCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(long catId);

    Category updateCategoryById(long catId, NewCategoryDto newCategoryDto);
}