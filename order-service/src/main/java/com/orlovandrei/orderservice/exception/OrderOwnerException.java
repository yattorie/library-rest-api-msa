package com.orlovandrei.orderservice.exception;

public class OrderOwnerException extends RuntimeException {
    public OrderOwnerException(String message) {
        super(message);
    }
}
