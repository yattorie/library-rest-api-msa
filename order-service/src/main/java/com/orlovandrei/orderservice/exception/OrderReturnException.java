package com.orlovandrei.orderservice.exception;

public class OrderReturnException extends RuntimeException {
    public OrderReturnException(String message) {
        super(message);
    }
}
