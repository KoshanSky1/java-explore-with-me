package ru.yandex.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.event.model.enums.EventStateAction;
import ru.yandex.practicum.event.model.Location;
import ru.yandex.practicum.event.model.enums.StateAction;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    //@NotNull
    //@NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    //@NotNull
    //@NotBlank
    @Length(min = 20, max = 7000)
    private String description;
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    //@NotNull
    //@NotBlank
    @Length(min = 3, max = 120)
    private String title;
}
