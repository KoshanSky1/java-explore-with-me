package ru.yandex.practicum.event.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.NotBlank;
import ru.yandex.practicum.event.model.enums.EventStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Integer> requestIds;
    @NotBlank
    private String status;
}