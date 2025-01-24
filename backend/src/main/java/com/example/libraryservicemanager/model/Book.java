package com.example.libraryservicemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Title;

    private String Author;

    private String isbn;

    private Integer quantity;

    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Lob
    @Column(name = "cover_image")
    private byte[] coverImage;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<BorrowedBooks> borrowedBooks = new ArrayList<>();
}
