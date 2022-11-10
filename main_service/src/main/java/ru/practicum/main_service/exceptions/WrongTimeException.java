package ru.practicum.main_service.exceptions;

public class WrongTimeException extends RuntimeException {

    public WrongTimeException(final String message) {
        super(message);
    }
}