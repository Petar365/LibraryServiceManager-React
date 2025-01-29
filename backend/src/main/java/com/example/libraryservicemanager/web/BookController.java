package com.example.libraryservicemanager.web;

import com.example.libraryservicemanager.model.Book;
import java.util.*;

import com.example.libraryservicemanager.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/books")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks(){
        return (bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById (@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(Book book){
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book editBook(@PathVariable Long id,
                         @RequestBody Book updateBook){
        return bookService.editBook(id,updateBook);
    }

    @PostMapping("/{id}/upload-cover")
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            bookService.saveCoverImage(id, file);
            return ResponseEntity.ok("Cover image uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload cover image.");
        }
    }
    @GetMapping("/{id}/cover")
    public ResponseEntity<String> getCoverImage(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book.getCoverImage() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cover image found for this book.");
        }

        String base64Image = Base64.getEncoder().encodeToString(book.getCoverImage());
        return ResponseEntity.ok(base64Image);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }
}
