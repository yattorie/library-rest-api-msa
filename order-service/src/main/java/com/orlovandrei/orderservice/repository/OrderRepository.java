package com.orlovandrei.orderservice.repository;

import com.orlovandrei.orderservice.entity.Order;
import com.orlovandrei.orderservice.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
    boolean existsByUserIdAndBookIdAndStatusIn(Long userId, Long bookId, List<OrderStatus> statuses);}