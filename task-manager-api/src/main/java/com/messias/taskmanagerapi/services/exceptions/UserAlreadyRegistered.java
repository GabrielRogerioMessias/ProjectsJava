package com.messias.taskmanagerapi.services.exceptions;

public class UserAlreadyRegistered extends RuntimeException {

    public UserAlreadyRegistered(String message) {
        super(message);
    }

}
