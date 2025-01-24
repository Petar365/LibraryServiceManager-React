package com.example.libraryservicemanager.repository;

import com.example.libraryservicemanager.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
