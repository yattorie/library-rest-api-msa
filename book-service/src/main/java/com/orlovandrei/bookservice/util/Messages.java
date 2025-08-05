package com.orlovandrei.bookservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    BOOK_NOT_FOUND_BY_ID("Book not found by id: "),
    BOOK_ALREADY_EXISTS("Book already exists: ");

    private final String message;
}
