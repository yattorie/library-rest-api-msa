package com.orlovandrei.bookservice.exception;

import com.orlovandrei.bookservice.util.LoggerUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleBookNotFoundExceptionException(BookNotFoundException e) {
        LoggerUtil.logError("Book not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleBookAlreadyExists(BookAlreadyExistsException e) {
        LoggerUtil.logError("Book already exists: " + e.getMessage(), e);
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e) {
        LoggerUtil.logError("Internal server error", e);
        return new ExceptionBody("Internal error");
    }

    @ExceptionHandler(InvalidBookStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleInvalidBookStateException(InvalidBookStateException e) {
        LoggerUtil.logError("Invalid available copies: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }
}
