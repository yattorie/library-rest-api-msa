package com.orlovandrei.bookservice.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating a new book")
public class UpdateBookRequest {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must be less than 255 characters")
    @Schema(
            description = "Title of the book",
            example = "The Great Gatsby"
    )
    String title;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = 255, message = "Author name must be less than 255 characters")
    @Schema(
            description = "Author of the book",
            example = "F. Scott Fitzgerald"
    )
    String author;

    @NotNull(message = "Publication year cannot be null")
    @PastOrPresent(message = "Publication year must be in the past or present")
    @Schema(
            description = "Publication date of the book",
            example = "1925-04-10"
    )
    LocalDate publicationYear;

    @Size(max = 100, message = "Genre must be less than 100 characters")
    @Schema(
            description = "Genre of the book",
            example = "Classic Literature"
    )
    String genre;

    @Size(max = 255, message = "Publisher name must be less than 255 characters")
    @Schema(
            description = "Publisher of the book",
            example = "Charles Scribner's Sons"
    )
    String publisher;

    @NotNull(message = "Total copies cannot be null")
    @PositiveOrZero(message = "Total copies cannot be negative")
    @Schema(
            description = "Total number of copies available",
            example = "10"
    )
    Integer totalCopies;

    @NotNull(message = "Available copies cannot be null")
    @PositiveOrZero(message = "Available copies cannot be negative")
    @Schema(
            description = "Number of copies currently available for loan",
            example = "8"
    )
    Integer availableCopies;
}
