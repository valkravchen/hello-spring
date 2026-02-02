package com.dobrynya.hellospring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
/**
 * @RestControllerAdvice - глобальный обработчик.
 * Это аннотация говорит Spring:
 * "здесь обработчики исключений для всех контроллеров".
 */
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    /**
     * @ExceptionHandler(SomeException.class) - метод вызывапется,
     * когда выбрасывется указанное исключение.
     */
    public ResponseEntity<String> handlerBookNotFound(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
