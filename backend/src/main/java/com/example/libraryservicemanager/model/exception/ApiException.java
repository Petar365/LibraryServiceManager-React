package com.example.libraryservicemanager.model.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
