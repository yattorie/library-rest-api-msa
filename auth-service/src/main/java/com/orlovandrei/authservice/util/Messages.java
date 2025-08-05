package com.orlovandrei.authservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    EMAIL("Email "),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),
    IS_ALREADY_TAKEN(" is already taken."),
    USERNAME("Username "),
    USER_NOT_FOUND("User not found");

    private final String message;
}
