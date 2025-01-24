package com.example.libraryservicemanager.repository;

import com.example.libraryservicemanager.model.BorrowedBooks;
//import com.example.libraryservicemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedBooksRepository extends JpaRepository<BorrowedBooks,Long> {

//    List<BorrowedBooks> findAllByUser (User user);
}
