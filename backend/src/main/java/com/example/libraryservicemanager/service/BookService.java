package com.example.libraryservicemanager.service;

import com.example.libraryservicemanager.model.Book;

import java.util.*;

public interface BookService {

    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    Book editBook(Long id,Book book);
    void deleteBook(Long id);
}
