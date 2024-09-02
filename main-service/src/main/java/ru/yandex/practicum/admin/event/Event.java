package ru.yandex.practicum.admin.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.admin.category.Category;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.event.EventState;
import ru.yandex.practicum.event.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "annotation")
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    @Column(name = "description")
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "state")
    private EventState state;
    @Column(name = "title")
    private String title;
    @Column(name = "views")
    private Integer views;
}