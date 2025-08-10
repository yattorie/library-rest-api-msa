package com.orlovandrei.orderservice.exception;

public class OrderCancelException extends RuntimeException {
    public OrderCancelException(String message) {
        super(message);
    }
}
