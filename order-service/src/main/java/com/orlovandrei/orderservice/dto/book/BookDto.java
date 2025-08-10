package com.orlovandrei.orderservice.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Book data transfer object")
public class BookDto {
    @Schema(
            description = "Unique identifier of the book",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Title of the book",
            example = "The Lord of the Rings"
    )
    String title;

    @Schema(
            description = "Author of the book",
            example = "J.R.R. Tolkien"
    )
    String author;

    @Schema(
            description = "Publication year of the book",
            example = "1975-04-10"
    )
    LocalDate publicationYear;

    @Schema(
            description = "Genre of the book",
            example = "Fantasy"
    )
    String genre;

    @Schema(
            description = "Publisher of the book",
            example = "Allen & Unwin"
    )
    String publisher;

    @Schema(
            description = "Total number of copies available",
            example = "10"
    )
    Integer totalCopies;

    @Schema(
            description = "Number of copies currently available",
            example = "10"
    )
    Integer availableCopies;

    @Schema(
            description = "When the book was added to the system"
    )
    LocalDateTime addedAt;
}

