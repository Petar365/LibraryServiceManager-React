package com.example.libraryservicemanager.model.exception;

public class BookIdNotFoundException extends RuntimeException{
    public BookIdNotFoundException(Long id) {
        super( String.format("Book with id %s is not found",id));
    }
}
