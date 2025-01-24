package com.example.libraryservicemanager.service;

import com.example.libraryservicemanager.model.BorrowedBooks;

import java.util.*;

public interface BorrowedBooksService {

    List<BorrowedBooks> getAllBorrowedBooks();

    BorrowedBooks getBorrowedBooksById(Long id);

    BorrowedBooks borrowBook(Long userId, Long bookId);

    BorrowedBooks returnBook(Long borrowedBookId);

    List<BorrowedBooks> getBorrowedBooksByUser(Long userId);
}
