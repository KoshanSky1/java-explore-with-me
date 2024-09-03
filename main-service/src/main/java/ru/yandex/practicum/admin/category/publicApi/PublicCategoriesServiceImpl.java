package ru.yandex.practicum.admin.category.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.category.CategoryRepository;
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
        return repository.findById(catId).orElseThrow(NotFoundException::new);
    }
}
