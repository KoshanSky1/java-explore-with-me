package ru.yandex.practicum.admin.category.publicApi;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.event.Event;

import java.util.List;

public interface PublicCategoriesRepository extends JpaRepository<Category, Long> {


}