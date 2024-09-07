package ru.yandex.practicum.category.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.repository.CategoryRepository;
import ru.yandex.practicum.category.dto.NewCategoryDto;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.error.IncorrectParameterException;
import ru.yandex.practicum.event.repository.EventRepository;

import static ru.yandex.practicum.category.dto.CategoryMapper.toCategoryFromNewCategoryDto;

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
        System.out.println(category);
        if (eventRepository.existsByCategory(category)) {
            System.out.println(eventRepository.existsByCategory(category));
            throw new ConflictException("For the requested operation the conditions are not met.");
        } else {
            repository.deleteById(catId);
        }
    }

    @Override
    public Category updateCategoryById(long catId, NewCategoryDto newCategoryDto) {
        Category category = repository.findById(catId).orElseThrow();
        System.out.println(newCategoryDto.getName().length());

        if (newCategoryDto.getName().length() > 50) {
            throw new IncorrectParameterException("---");
        }
            try {
                category.setName(newCategoryDto.getName());
                repository.save(category);
            } catch (DataIntegrityViolationException e) {
                throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
                        " nested exception is org.hibernate.exception.ConstraintViolationException: "
                        + "could not execute statement");
            }

        return category;
    }

}