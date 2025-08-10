package com.orlovandrei.orderservice.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request to create new order")
public class CreateOrderRequest {
    @NotNull(message = "Book ID cannot be null")
    @Schema(
            description = "ID of the book to order",
            example = "1"
    )
    Long bookId;
}