package com.orlovandrei.orderservice.exception;

import com.orlovandrei.orderservice.util.LoggerUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleOrderNotFoundException(OrderNotFoundException e) {
        LoggerUtil.logError("Order not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(BookNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleBookNotAvailable(BookNotAvailableException e) {
        LoggerUtil.logError("Book not available: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleBookNotFoundException(BookNotFoundException e) {
        LoggerUtil.logError("Book not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleUserNotFoundException(UserNotFoundException e) {
        LoggerUtil.logError("User not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(IllegalStateException e) {
        LoggerUtil.logError("Illegal state: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceNotFoundException(MethodArgumentNotValidException e) {
        LoggerUtil.logError("Validation failed: " + e.getMessage(), e);
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(fieldErrors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(ConstraintViolationException e) {
        LoggerUtil.logError("Constraint violation: " + e.getMessage(), e);
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        exceptionBody.setErrors(e.getConstraintViolations().stream().collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                violation -> violation.getMessage())));
        return exceptionBody;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e) {
        LoggerUtil.logError("Internal server error", e);
        return new ExceptionBody("Internal error");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleAuthenticationException(AuthenticationException e) {
        LoggerUtil.logError("Authentication failed: " + e.getMessage(), e);
        return new ExceptionBody("Authentication failed");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDeniedException(AccessDeniedException e) {
        LoggerUtil.logError("Access denied: " + e.getMessage(), e);
        return new ExceptionBody("Access denied: You do not have permission to perform this action");
    }

    @ExceptionHandler(OrderApproveException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleOrderApproveException(OrderApproveException e) {
        LoggerUtil.logError("Order approve error: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(OrderReturnException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleOrderReturnException(OrderReturnException e) {
        LoggerUtil.logError("Order return error: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(OrderOwnerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleOrderOwnerException(OrderOwnerException e) {
        LoggerUtil.logError("Order owner error: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(OrderCancelException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleOrderCancelException(OrderCancelException e) {
        LoggerUtil.logError("Order cancel error: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }



}