package com.orlovandrei.bookservice.controller;

import com.orlovandrei.bookservice.dto.book.BookDto;
import com.orlovandrei.bookservice.dto.internal.InternalUpdateAvailableCopiesRequest;
import com.orlovandrei.bookservice.service.BookService;
import com.orlovandrei.bookservice.util.Messages;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = " Internal Book Controller", description = "Operations for internal managing books")
public class InternalBookController {

    private final BookService bookService;

    @PutMapping("/internal/{id}/available-copies")
    public ResponseEntity<BookDto> updateAvailableCopies(
            @PathVariable Long id,
            @RequestBody InternalUpdateAvailableCopiesRequest request,
            @RequestHeader(value = "X-Internal-Call", required = false) String internalCallHeader
    ) {
        if (!"order-service".equals(internalCallHeader)) {
            throw new AccessDeniedException(Messages.ACCESS_DENIED_INTERNAL_UPDATE.getMessage());
        }
        return ResponseEntity.ok(bookService.updateAvailableCopies(id, request.getAvailableCopies()));
    }

}
