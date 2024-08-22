package ru.yandex.practicum.exception;

public class StatsValidationException extends RuntimeException {
    public StatsValidationException(String message) {
        super(message);
    }

}