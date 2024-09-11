package com.example.shoppingapp.services.exceptions;

import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException {

    public <T> ResourceNotFoundException(Class<T> entity, Integer id) {
        super(MessageFormat.format("{0} not fount with id {1}", entity.getSimpleName(), id));
    }
}
