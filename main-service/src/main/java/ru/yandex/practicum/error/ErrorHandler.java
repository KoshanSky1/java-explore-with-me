package ru.yandex.practicum.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIncorrectParameterException(IncorrectParameterException e) {
        return new ApiError(
                "BAD_REQUEST",
                "Incorrectly made request.",
                "Failed to convert value of type java.lang.String to required type long; " +
                        "nested exception is java.lang.NumberFormatException: For input string: ad",
                LocalDateTime.now().toString()
        );
    }
}
