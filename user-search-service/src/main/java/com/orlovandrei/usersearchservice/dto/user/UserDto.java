package com.orlovandrei.usersearchservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "User data transfer object")
public class UserDto {
    @Schema(
            description = "Unique identifier of the user",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Username of the user",
            example = "test_user"
    )
    String username;

    @Schema(
            description = "Email address of the user",
            example = "test_user@example.com"
    )
    String email;
}