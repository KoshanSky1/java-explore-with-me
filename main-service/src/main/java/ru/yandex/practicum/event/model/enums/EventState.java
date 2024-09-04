package ru.yandex.practicum.event.model.enums;

public enum EventState {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static EventState getEventState(String state) {
        for (EventState es: EventState.values()) {
            if (es.name().equalsIgnoreCase(state)) {
                return es;
            }
        }
        return null;
    }
}
