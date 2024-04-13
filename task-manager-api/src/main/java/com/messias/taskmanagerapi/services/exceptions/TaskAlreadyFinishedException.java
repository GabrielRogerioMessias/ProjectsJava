package com.messias.taskmanagerapi.services.exceptions;

public class TaskAlreadyFinishedException extends RuntimeException {
    public TaskAlreadyFinishedException(String msg) {
        super(msg);
    }

}
