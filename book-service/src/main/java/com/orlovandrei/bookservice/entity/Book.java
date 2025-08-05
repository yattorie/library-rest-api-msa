package com.orlovandrei.bookservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "books")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "author")
    String author;

    @Column(name = "publication_year")
    LocalDate publicationYear;

    @Column(name = "genre")
    String genre;

    @Column(name = "publisher")
    String publisher;

    @Column(name = "total_copies")
    Integer totalCopies;

    @Column(name = "available_copies")
    Integer availableCopies;

    @CreationTimestamp
    LocalDateTime addedAt;
}
