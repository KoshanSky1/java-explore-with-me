package ru.yandex.practicum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VievStats {
    private String app;
    private String uri;
    private Integer hits;
}
