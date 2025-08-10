package com.orlovandrei.orderservice.service;

import com.orlovandrei.orderservice.dto.order.CreateOrderRequest;
import com.orlovandrei.orderservice.dto.order.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional
    OrderDto create(CreateOrderRequest request, String username);

    @Transactional(readOnly = true)
    Page<OrderDto> getAll(int page, int size);

    @Transactional(readOnly = true)
    OrderDto getById(Long id);

    @Transactional(readOnly = true)
    Page<OrderDto> getByUsername(String username, int page, int size);

    @Transactional
    OrderDto approveOrder(Long id);

    @Transactional
    OrderDto returnBook(Long id);

    @Transactional
    OrderDto cancelOrder(Long id, String username);

    @Transactional
    void delete(Long id);

    @Transactional(readOnly = true)
    Page<OrderDto> getByUserId(Long userId, int page, int size);
}