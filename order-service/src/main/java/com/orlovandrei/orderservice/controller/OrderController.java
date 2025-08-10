package com.orlovandrei.orderservice.controller;

import com.orlovandrei.orderservice.dto.order.CreateOrderRequest;
import com.orlovandrei.orderservice.dto.order.OrderDto;
import com.orlovandrei.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Controller", description = "Operations for managing book orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getAll(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get current user's orders")
    public ResponseEntity<Page<OrderDto>> getCurrentUserOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getByUsername(userDetails.getUsername(), page, size));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get orders by user ID")
    public ResponseEntity<Page<OrderDto>> getByUserId(
            @PathVariable("id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getByUserId(userId, page, size));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderDto> create(@RequestBody @Valid CreateOrderRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.create(request, userDetails.getUsername()));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve an order")
    public ResponseEntity<OrderDto> approveOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.approveOrder(id));
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Return a book")
    public ResponseEntity<OrderDto> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.returnBook(id));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Cancel an order")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.cancelOrder(id, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}