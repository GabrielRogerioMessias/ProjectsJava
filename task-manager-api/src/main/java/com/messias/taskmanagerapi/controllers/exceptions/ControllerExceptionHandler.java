package com.messias.taskmanagerapi.controllers.exceptions;

import com.messias.taskmanagerapi.security.exceptions.CustomBadCredentialsException;
import com.messias.taskmanagerapi.security.exceptions.InvalidJwtAuthenticationException;
import com.messias.taskmanagerapi.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyRegistered.class)
    public ResponseEntity<StandardError> userAlreadyRegistered(UserAlreadyRegistered e, HttpServletRequest request) {
        String error = "User already registered";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> userAlreadyRegistered(ResourceNotFoundException e,
                                                               HttpServletRequest request) {
        String error = "Resource not found exception";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TaskAlreadyFinishedException.class)
    public ResponseEntity<StandardError> taskAlreadyFinished(TaskAlreadyFinishedException e,
                                                             HttpServletRequest request) {
        String error = "Task already finished";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NullEntityFieldException.class)
    public ResponseEntity<StandardError> userFieldsNull(NullEntityFieldException e, HttpServletRequest request) {
        String error = "User fields cannot be null or empty";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PasswordIsNotPatterException.class)
    public ResponseEntity<StandardError> userFieldsNull(PasswordIsNotPatterException e, HttpServletRequest request) {
        String error = "Password is not in patter";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<StandardError> cannotScheduleException(InvalidJwtAuthenticationException e, HttpServletRequest request) {
        String error = "Invalid JWT Authentication";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<StandardError> cannotScheduleException(CustomBadCredentialsException e, HttpServletRequest request) {
        String error = "Username or Password Invalid";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }
}
