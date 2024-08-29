package com.example.shoppingapp.controllers.exceptions;

import com.example.shoppingapp.services.exceptions.NullEntityFieldException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NullEntityFieldException.class)
    public ResponseEntity<StandartError> productFieldsNull(NullEntityFieldException e, HttpServletRequest request) {
        String error = "Category Fields Null";
        HttpStatus status = HttpStatus.CONFLICT;
        StandartError err = new StandartError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}

