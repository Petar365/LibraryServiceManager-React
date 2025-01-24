//package com.example.libraryservicemanager.web;
//
//import com.example.libraryservicemanager.model.BorrowedBooks;
//import com.example.libraryservicemanager.service.BorrowedBooksService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/borrowed-books")
//@RequiredArgsConstructor
//public class BorrowedBooksController {
//
//    private BorrowedBooksService borrowedBooksService;
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<BorrowedBooks> getAllBorrowedBooks(){
//        return borrowedBooksService.getAllBorrowedBooks();
//    }
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public BorrowedBooks getBorrowedBookById(@PathVariable Long id){
//        return borrowedBooksService.getBorrowedBooksById(id);
//    }
//
//    @PostMapping("/borrow")
//    @ResponseStatus(HttpStatus.CREATED)
//    public BorrowedBooks borrowBook(@RequestParam Long userId,
//                                    @RequestParam Long bookId){
//        return borrowedBooksService.borrowBook(userId, bookId);
//    }
//
//    @PostMapping("/return/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public BorrowedBooks returnBook(@PathVariable Long id){
//        return borrowedBooksService.returnBook(id);
//    }
//
//    @GetMapping("/user/{userId}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<BorrowedBooks> getBooksByUser(@PathVariable Long userId){
//        return borrowedBooksService.getBorrowedBooksByUser(userId);
//    }
//}
