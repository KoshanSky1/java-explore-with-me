package ru.yandex.practicum.category.publicApi;

import ru.yandex.practicum.category.model.Category;

import java.util.List;

public interface PublicCategoriesService {
    List<Category> getCategories();

    Category getCategoryById(Long catId);
}
