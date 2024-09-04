package ru.yandex.practicum.users.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
    @NotBlank
    @NotNull
    @NotEmpty
    @Email
    @Size(min = 6)
    @Size(max = 255)
    private String email;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 2)
    @Size(max = 250)
    private String name;
}