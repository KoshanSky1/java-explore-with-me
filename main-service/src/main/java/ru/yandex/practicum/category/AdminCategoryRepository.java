package ru.yandex.practicum.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCategoryRepository extends JpaRepository<Category, Long> {

}