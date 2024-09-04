package ru.yandex.practicum.event.model.enums;

public enum EventStatus {
    CONFIRMED,
    REJECTED,
    PENDING,
    CANCELED;

    public static EventStatus getEventStatus(String state) {
        for (EventStatus es: EventStatus.values()) {
            if (es.name().equalsIgnoreCase(state)) {
                return es;
            }
        }
        return null;
    }
}
