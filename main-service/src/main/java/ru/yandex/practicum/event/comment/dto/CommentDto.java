package ru.yandex.practicum.event.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.event.model.Event;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String text;
    private Event event;
    private String authorName;
    private LocalDateTime created;
    private LocalDateTime changed;
}