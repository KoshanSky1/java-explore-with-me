package ru.yandex.practicum.error;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}