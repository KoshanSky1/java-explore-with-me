package ru.yandex.practicum.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.admin.category.dto.CategoryDto;
import ru.yandex.practicum.admin.users.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime eventDate;
    private UserShortDto user;
    private Boolean paid;
    private String title;
    private Integer views;
}
