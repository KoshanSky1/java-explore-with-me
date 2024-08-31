package ru.yandex.practicum.admin.category.publicApi;

import ru.yandex.practicum.admin.category.Category;

import java.util.List;
import java.util.Optional;

public interface PublicCategoriesService {
    List<Category> getCategories();

    Optional<Category> getCategoryById(Long catId);
}
