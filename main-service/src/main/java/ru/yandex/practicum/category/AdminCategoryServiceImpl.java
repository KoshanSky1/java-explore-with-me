package ru.yandex.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.dto.CategoryDto;

import static ru.yandex.practicum.category.dto.CategoryMapper.toCategory;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final AdminCategoryRepository repository;


    @Override
    public Category addCategory(CategoryDto categoryDto) {
        return repository.save(toCategory(categoryDto));
    }

    @Override
    public void deleteCategoryById(long catId) {
        repository.deleteById(catId);
    }

    @Override
    public Category updateCategoryById(long catId, CategoryDto categoryDto) {
        //Optional<Category> category = repository.findById(catId);
        return repository.save(toCategory(categoryDto));
    }
}