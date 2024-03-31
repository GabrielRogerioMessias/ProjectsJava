package com.messias.taskmanagerapi.services.exceptions;

public class ResourceAlreadyRegisteredException extends RuntimeException {
    public ResourceAlreadyRegisteredException() {
    }

    public ResourceAlreadyRegisteredException(Class<?> className, String name) {
        super(className.getSimpleName() + " already registered with description : " + name);
    }
}
