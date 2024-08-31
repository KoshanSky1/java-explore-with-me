package ru.yandex.practicum.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.admin.users.User;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.admin.event.EventStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EventStatus status;
}