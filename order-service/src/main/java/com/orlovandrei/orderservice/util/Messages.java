package com.orlovandrei.orderservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    ORDER_NOT_FOUND_BY_ID("Order not found with id: "),
    ORDER_APPROVE_ERROR("Only PENDING orders can be approved"),
    ORDER_RETURN_ERROR("Only APPROVED orders can be returned"),
    ORDER_CANCEL_ERROR("Only PENDING orders can be cancelled"),
    ORDER_CANCEL_OWNER_ERROR("You can only cancel your own orders"),
    USER_NOT_FOUND_USERNAME("User not found with username: "),
    BOOK_NOT_FOUND("Book not found with id: "),
    BOOK_ALREADY_ORDERED("You already have an active order for this book"),
    NO_AVAILABLE_COPIES("No available copies for book with id: ");

    private final String message;
}