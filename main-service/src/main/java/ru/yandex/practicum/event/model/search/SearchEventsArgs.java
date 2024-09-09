package ru.yandex.practicum.event.model.search;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEventsArgs {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    //@Future
    private LocalDateTime rangeStart;
    //@Future
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private String sort;
    //private Integer from;
    //rivate Integer size;
    private HttpServletRequest request;
}

