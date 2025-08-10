package com.orlovandrei.orderservice.dto.internal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Internal DTO for transferring minimal user data between services")
public class InternalUserDto {
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
}
