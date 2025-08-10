package com.orlovandrei.orderservice.dto.order;

import com.orlovandrei.orderservice.entity.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Order data transfer object")
public class OrderDto {
    @Schema(
            description = "Unique identifier of the order",
            example = "1"
    )
    Long id;

    @Schema(
            description = "ID of the user who made the order",
            example = "123"
    )
    Long userId;

    @Schema(
            description = "ID of the ordered book",
            example = "456"
    )
    Long bookId;

    @Schema(
            description = "Current status of the order",
            example = "APPROVED"
    )
    OrderStatus status;

    @Schema(
            description = "When the order was created",
            example = "2025-01-15T10:30:00"
    )
    LocalDateTime createdAt;

    @Schema(
            description = "Planned return date (for approved orders)",
            example = "2025-01-29T10:30:00"
    )
    LocalDateTime returnDate;

    @Schema(
            description = "When the order was actually returned (for returned orders)",
            example = "2025-05-28T09:15:00"
    )
    LocalDateTime returnedAt;
}