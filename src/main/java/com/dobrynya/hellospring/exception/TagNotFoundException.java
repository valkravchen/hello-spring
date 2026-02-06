package com.dobrynya.hellospring.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long id) {
        super("Тег с id " + id + " не найден");
    }
}
