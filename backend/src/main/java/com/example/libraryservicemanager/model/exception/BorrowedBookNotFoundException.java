package com.example.libraryservicemanager.model.exception;

public class BorrowedBookNotFoundException extends RuntimeException{
    public BorrowedBookNotFoundException(Long id) {
        super(String.format("BorrowedBook with id %s is not found",id));
    }
}
