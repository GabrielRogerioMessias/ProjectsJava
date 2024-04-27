package com.messias.taskmanagerapi.services.exceptions;

import java.util.List;

public class NullEntityFieldException extends RuntimeException {
   

    public NullEntityFieldException(List<String> messagesError) {
        super(String.valueOf(messagesError) + " field(s) cannot be null or empty");
    }
}
