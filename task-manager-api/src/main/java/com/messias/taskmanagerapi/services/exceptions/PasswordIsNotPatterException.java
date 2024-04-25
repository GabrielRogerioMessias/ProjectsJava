package com.messias.taskmanagerapi.services.exceptions;

public class PasswordIsNotPatterException extends RuntimeException {
    public PasswordIsNotPatterException() {
        super("InvalidPasswordException: The password must meet the following criteria: it must contain at least one letter (uppercase or lowercase)," +
                " at least one digit, at least one special character that is neither a letter nor a digit, and it must have a minimum length of 8 characters");
    }
}
