package com.orlovandrei.bookservice.exception;

public class InvalidBookStateException extends RuntimeException {
    public InvalidBookStateException(String message) {
        super(message);
    }
}
