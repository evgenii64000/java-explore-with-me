package ru.practicum.main_service.exceptions;

public class WrongIdException extends RuntimeException {

    public WrongIdException(final String message) {
        super(message);
    }
}
