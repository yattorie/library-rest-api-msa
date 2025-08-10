package com.orlovandrei.orderservice.service.impl;

import com.orlovandrei.orderservice.client.BookServiceClient;
import com.orlovandrei.orderservice.client.UserSearchServiceClient;
import com.orlovandrei.orderservice.dto.book.BookDto;
import com.orlovandrei.orderservice.dto.internal.InternalUserDto;
import com.orlovandrei.orderservice.dto.internal.InternalUpdateAvailableCopiesRequest;
import com.orlovandrei.orderservice.dto.order.CreateOrderRequest;
import com.orlovandrei.orderservice.dto.order.OrderDto;
import com.orlovandrei.orderservice.dto.mapper.OrderMapper;
import com.orlovandrei.orderservice.entity.Order;
import com.orlovandrei.orderservice.entity.enums.OrderStatus;
import com.orlovandrei.orderservice.exception.BookNotAvailableException;
import com.orlovandrei.orderservice.exception.BookNotFoundException;
import com.orlovandrei.orderservice.exception.OrderApproveException;
import com.orlovandrei.orderservice.exception.OrderCancelException;
import com.orlovandrei.orderservice.exception.OrderNotFoundException;
import com.orlovandrei.orderservice.exception.OrderOwnerException;
import com.orlovandrei.orderservice.exception.OrderReturnException;
import com.orlovandrei.orderservice.exception.UserNotFoundException;
import com.orlovandrei.orderservice.repository.OrderRepository;
import com.orlovandrei.orderservice.service.OrderService;
import com.orlovandrei.orderservice.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final BookServiceClient bookServiceClient;
    private final UserSearchServiceClient userSearchServiceClient;

    @Transactional
    @Override
    public OrderDto create(CreateOrderRequest request, String username) {
        InternalUserDto user = userSearchServiceClient.getInternalByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(Messages.USER_NOT_FOUND_USERNAME.getMessage() + username);
        }

        BookDto book = bookServiceClient.getBookById(request.getBookId());
        if (book == null) {
            throw new BookNotFoundException(Messages.BOOK_NOT_FOUND.getMessage() + request.getBookId());
        }

        boolean hasActiveOrder = orderRepository.existsByUserIdAndBookIdAndStatusIn(
                user.getId(),
                request.getBookId(),
                List.of(OrderStatus.PENDING, OrderStatus.APPROVED)
        );

        if (hasActiveOrder) {
            throw new BookNotAvailableException(Messages.BOOK_ALREADY_ORDERED.getMessage());
        }

        Order order = Order.builder()
                .userId(user.getId())
                .bookId(request.getBookId())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDto> getAll(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size))
                .map(orderMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(Messages.ORDER_NOT_FOUND_BY_ID.getMessage() + id));
        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDto> getByUsername(String username, int page, int size) {
        InternalUserDto user = userSearchServiceClient.getInternalByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(Messages.USER_NOT_FOUND_USERNAME.getMessage() + username);
        }
        return orderRepository.findByUserId(user.getId(), PageRequest.of(page, size))
                .map(orderMapper::toDto);
    }

    @Transactional
    @Override
    public OrderDto approveOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(Messages.ORDER_NOT_FOUND_BY_ID.getMessage() + id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderApproveException(Messages.ORDER_APPROVE_ERROR.getMessage());
        }

        BookDto book = bookServiceClient.getBookById(order.getBookId());
        if (book == null) {
            throw new BookNotFoundException(Messages.BOOK_NOT_FOUND.getMessage() + order.getBookId());
        }

        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException(Messages.NO_AVAILABLE_COPIES.getMessage() + order.getBookId());
        }

        bookServiceClient.updateAvailableCopies(
                book.getId(),
                new InternalUpdateAvailableCopiesRequest(book.getAvailableCopies() - 1)
        );

        order.setStatus(OrderStatus.APPROVED);
        order.setReturnDate(LocalDateTime.now().plusDays(14));

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    @Override
    public OrderDto returnBook(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(Messages.ORDER_NOT_FOUND_BY_ID.getMessage() + id));

        if (order.getStatus() != OrderStatus.APPROVED) {
            throw new OrderReturnException(Messages.ORDER_RETURN_ERROR.getMessage());
        }

        BookDto book = bookServiceClient.getBookById(order.getBookId());
        if (book == null) {
            throw new BookNotFoundException(Messages.BOOK_NOT_FOUND.getMessage() + order.getBookId());
        }

        bookServiceClient.updateAvailableCopies(
                book.getId(),
                new InternalUpdateAvailableCopiesRequest(book.getAvailableCopies() + 1)
        );

        order.setStatus(OrderStatus.RETURNED);
        order.setReturnDate(LocalDateTime.now());

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    @Override
    public OrderDto cancelOrder(Long id, String username) {
        InternalUserDto user = userSearchServiceClient.getInternalByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(Messages.USER_NOT_FOUND_USERNAME.getMessage() + username);
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(Messages.ORDER_NOT_FOUND_BY_ID.getMessage() + id));

        if (!order.getUserId().equals(user.getId())) {
            throw new OrderOwnerException(Messages.ORDER_CANCEL_OWNER_ERROR.getMessage());
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderCancelException(Messages.ORDER_CANCEL_ERROR.getMessage());
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(Messages.ORDER_NOT_FOUND_BY_ID.getMessage() + id));
        if (order.getStatus() == OrderStatus.APPROVED) {
            BookDto book = bookServiceClient.getBookById(order.getBookId());
            bookServiceClient.updateAvailableCopies(
                    book.getId(),
                    new InternalUpdateAvailableCopiesRequest(book.getAvailableCopies() + 1)
            );
        }
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDto> getByUserId(Long userId, int page, int size) {
        return orderRepository.findByUserId(userId, PageRequest.of(page, size))
                .map(orderMapper::toDto);
    }
}