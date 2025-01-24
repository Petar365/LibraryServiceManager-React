package com.example.libraryservicemanager.service.impl;

import com.example.libraryservicemanager.model.Book;
import com.example.libraryservicemanager.model.exception.BookIdNotFoundException;
import com.example.libraryservicemanager.repository.BookRepository;
import com.example.libraryservicemanager.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookIdNotFoundException(id));
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book editBook(Long id, Book updateBook) {
        Book book = getBookById(id);
        book.setTitle(updateBook.getTitle());
        book.setAuthor(updateBook.getAuthor());
        book.setIsbn(updateBook.getIsbn());
        book.setQuantity(updateBook.getQuantity());
        book.setDescription(updateBook.getDescription());
        book.setReleaseDate(updateBook.getReleaseDate());
        book.setAddedAt(updateBook.getAddedAt());

        if (updateBook.getBorrowedBooks() != null) {
            book.setBorrowedBooks(updateBook.getBorrowedBooks());
        }

        return saveBook(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}
