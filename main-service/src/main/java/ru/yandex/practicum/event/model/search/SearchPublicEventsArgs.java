package ru.yandex.practicum.event.model.search;

import lombok.*;
import ru.yandex.practicum.event.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchPublicEventsArgs {
    private List<Long> users;
    private List<EventState> states;
    private List<Long> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private Integer from;
    private Integer size;
}
