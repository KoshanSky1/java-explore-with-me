package ru.yandex.practicum.error;

public class IncorrectParameterException extends RuntimeException {
    public IncorrectParameterException(final String message) {
        super(message);
    }

}