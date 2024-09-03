package ru.yandex.practicum.admin.category.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.category.CategoryRepository;
import ru.yandex.practicum.admin.category.dto.CategoryDto;
import ru.yandex.practicum.admin.category.dto.NewCategoryDto;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.privateAPI.EventRepository;
import ru.yandex.practicum.error.ConflictException;

import java.util.List;

import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryFromCategoryDto;
import static ru.yandex.practicum.admin.category.dto.CategoryMapper.toCategoryFromNewCategoryDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository repository;
    private final EventRepository eventRepository;

    @Override
    public Category addCategory(NewCategoryDto newCategoryDto) {
        Category category;
        try {
            category = repository.save(toCategoryFromNewCategoryDto(newCategoryDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
                    " nested exception is org.hibernate.exception.ConstraintViolationException: "
                    + "could not execute statement");
        }
        return category;
    }

    @Override
    public void deleteCategoryById(long catId) {
        Category category = repository.findById(catId).orElseThrow();
        List<Event> eventList = eventRepository.findAll();
        for (Event event : eventList) {
            if (event.getCategory().equals(category)) {
                throw new ConflictException("For the requested operation the conditions are not met.");
            }
        }
        repository.deleteById(catId);
    }

    @Override
    public Category updateCategoryById(long catId, CategoryDto categoryDto) {
        Category category;
        try {
            category = repository.save(toCategoryFromCategoryDto(categoryDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
                    " nested exception is org.hibernate.exception.ConstraintViolationException: "
                    + "could not execute statement");
        }
        return category;
    }

}