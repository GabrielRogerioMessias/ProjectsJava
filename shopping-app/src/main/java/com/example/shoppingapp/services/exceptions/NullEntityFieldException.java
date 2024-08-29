package com.example.shoppingapp.services.exceptions;

import java.util.List;

public class NullEntityFieldException extends RuntimeException {
    public NullEntityFieldException(List<String> messages) {
        super(String.valueOf(messages) + " field(s) cannot be null or empty");
    }
}
