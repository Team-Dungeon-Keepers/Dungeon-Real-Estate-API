package com.revature.dungeonsite.advice;

import com.revature.dungeonsite.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.noContent().build();
    }
}
