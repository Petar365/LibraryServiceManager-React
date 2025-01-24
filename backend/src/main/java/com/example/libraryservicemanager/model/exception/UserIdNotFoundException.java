package com.example.libraryservicemanager.model.exception;

public class UserIdNotFoundException extends RuntimeException{
    public UserIdNotFoundException(Long id) {
        super(String.format("User with id %s not found",id));
    }
}
