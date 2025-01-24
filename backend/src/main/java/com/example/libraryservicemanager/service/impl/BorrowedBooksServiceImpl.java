//package com.example.libraryservicemanager.service.impl;
//
//import com.example.libraryservicemanager.model.Book;
//import com.example.libraryservicemanager.model.BorrowedBooks;
//import com.example.libraryservicemanager.model.User;
//import com.example.libraryservicemanager.model.enumeration.Status;
//import com.example.libraryservicemanager.model.exception.BorrowedBookNotFoundException;
//import com.example.libraryservicemanager.repository.BorrowedBooksRepository;
//import com.example.libraryservicemanager.service.BookService;
//import com.example.libraryservicemanager.service.BorrowedBooksService;
//import com.example.libraryservicemanager.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class BorrowedBooksServiceImpl implements BorrowedBooksService {
//
//    private final BorrowedBooksRepository borrowedBooksRepository;
//    private final UserService userService;
//    private final BookService bookService;
//
//    @Override
//    public List<BorrowedBooks> getAllBorrowedBooks() {
//        return borrowedBooksRepository.findAll();
//    }
//
//    @Override
//    public BorrowedBooks getBorrowedBooksById(Long id) {
//        return borrowedBooksRepository.findById(id).orElseThrow(() -> new BorrowedBookNotFoundException(id));
//    }
//
//    @Override
//    public BorrowedBooks borrowBook(Long userId, Long bookId) {
//        User user = userService.findUserById(userId);
//        Book book = bookService.getBookById(userId);
//
//        if (book.getQuantity() <= 0) {
//            throw new IllegalArgumentException("Book out of Stock");
//        }
//
//        BorrowedBooks borrowedBook = new BorrowedBooks();
//
//        borrowedBook.setUser(user);
//        borrowedBook.setBook(book);
//        borrowedBook.setBorrowDate(LocalDate.now());
//        borrowedBook.setStatus(Status.BORROWED);
//
//        book.setQuantity(book.getQuantity() - 1);
//        bookService.editBook(book.getId(), book);
//
//        return borrowedBooksRepository.save(borrowedBook);
//    }
//
//    @Override
//    public BorrowedBooks returnBook(Long borrowedBookId) {
//
//        BorrowedBooks borrowedBook = getBorrowedBooksById(borrowedBookId);
//
//        if (!Status.BORROWED.equals(borrowedBook.getStatus())) {
//            throw new IllegalArgumentException("Book is not currently borrowed");
//        }
//
//        borrowedBook.setStatus(Status.RETURNED);
//        borrowedBook.setReturnDate(LocalDate.now());
//
//        Book book = borrowedBook.getBook();
//        book.setQuantity(book.getQuantity() + 1);
//        bookService.editBook(book.getId(),book);
//
//        return borrowedBooksRepository.save(borrowedBook);
//    }
//
//    @Override
//    public List<BorrowedBooks> getBorrowedBooksByUser(Long userId) {
//        User user = userService.findUserById(userId);
//
//        return borrowedBooksRepository.findAllByUser(user);
//    }
//}
