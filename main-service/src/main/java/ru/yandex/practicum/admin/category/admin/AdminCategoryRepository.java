package ru.yandex.practicum.admin.category.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.admin.category.Category;

public interface AdminCategoryRepository extends JpaRepository<Category, Long> {

}