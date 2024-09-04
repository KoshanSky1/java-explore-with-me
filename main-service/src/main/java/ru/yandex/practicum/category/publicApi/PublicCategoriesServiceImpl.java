package ru.yandex.practicum.category.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.repository.CategoryRepository;
import ru.yandex.practicum.error.NotFoundException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicCategoriesServiceImpl implements PublicCategoriesService {
    private final CategoryRepository repository;

    @Override
    public List<Category> getCategories() {
        return repository.findAll();
    }

    @Override
    public Category getCategoryById(Long catId) {
        return repository.findById(catId).orElseThrow(()
                -> new NotFoundException("Category not found with id = " + catId));
    }
}
