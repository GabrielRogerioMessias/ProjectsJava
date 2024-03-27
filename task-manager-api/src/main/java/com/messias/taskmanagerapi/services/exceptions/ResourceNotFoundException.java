package com.messias.taskmanagerapi.services.exceptions;


import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
    public ResourceNotFoundException(Class<?> nameClass, UUID idEntity){
        super(nameClass.getSimpleName() + " not found with UUID: " + idEntity);
    }
}
