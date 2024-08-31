package ru.yandex.practicum.admin.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.event.EventStateAction;
import ru.yandex.practicum.event.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    //private Long id;
    private String annotation;
    private Integer category;
    private String description;
   // private Integer confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    //private UserShortDto user;
    private Boolean paid;
    private Boolean participantLimit;
    private Boolean requestModeration;
    private EventStateAction stateAction;
    private String title;
    //private Integer views;
}