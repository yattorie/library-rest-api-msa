package com.orlovandrei.bookservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    BOOK_NOT_FOUND_BY_ID("Book not found by id: "),
    BOOK_ALREADY_EXISTS("Book already exists: "),
    AVAILABLE_COPIES_CANNOT_BE_NEGATIVE("Available copies cannot be negative"),
    ACCESS_DENIED_INTERNAL_UPDATE("Only order-service can update available copies");
    private final String message;
}
