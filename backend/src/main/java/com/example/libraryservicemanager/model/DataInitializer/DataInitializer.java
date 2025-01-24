//package com.example.libraryservicemanager.model.DataInitializer;
//
//import com.example.libraryservicemanager.model.Book;
//import com.example.libraryservicemanager.repository.BookRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final BookRepository bookRepository;
//
//    public DataInitializer(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//
//        // Initialize books if the database is empty
//        if (bookRepository.count() == 0) {
//            Book book1 = new Book(null, "Spring Boot in Action", "Craig Walls", "9781617292545", 10, LocalDateTime.now(), null);
//            Book book2 = new Book(null, "Learning Java", "Patrick Niemeyer", "9781449365035", 5, LocalDateTime.now(), null);
//            Book book3 = new Book(null, "To Kill a Mockingbird", "Harper Lee", "9780061120084", 5, LocalDateTime.now(), null);
//            Book book4 = new Book(null, "Moby Dick", "Herman Melville", "9781503280786", 3, LocalDateTime.now(), null);
//            Book book5 = new Book(null, "War and Peace", "Leo Tolstoy", "9781420954303", 7, LocalDateTime.now(), null);
//
//            bookRepository.save(book1);
//            bookRepository.save(book2);
//            bookRepository.save(book3);
//            bookRepository.save(book4);
//            bookRepository.save(book5);
//        }
//    }
//}
