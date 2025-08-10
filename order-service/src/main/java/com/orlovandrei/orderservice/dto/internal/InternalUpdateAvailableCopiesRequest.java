package com.orlovandrei.orderservice.dto.internal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Internal request for updating available copies of a book")
public class InternalUpdateAvailableCopiesRequest {

    @NotNull(message = "Available copies cannot be null")
    @PositiveOrZero(message = "Available copies cannot be negative")
    @Schema(
            description = "Number of copies currently available",
            example = "5"
    )
    Integer availableCopies;
}
