package com.orlovandrei.usersearchservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    USER_NOT_FOUND_ID("User not found with id: "),
    USER_NOT_FOUND_USERNAME("User not found with username: "),
    USER_NOT_FOUND_EMAIL("User not found with email: ");
    private final String message;
}