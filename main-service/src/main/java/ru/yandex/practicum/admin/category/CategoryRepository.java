package ru.yandex.practicum.admin.category;//package ru.yandex.practicum.admin.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.admin.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}