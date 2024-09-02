package ru.yandex.practicum.admin.category.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicCategoriesServiceImpl implements PublicCategoriesService {
    CategoryRepository publicCategoriesRepository;

    @Override
    public List<Category> getCategories() {
        return publicCategoriesRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long catId) {
        return publicCategoriesRepository.findById(catId);
    }
}
