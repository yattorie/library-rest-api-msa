package com.orlovandrei.orderservice.client;

import com.orlovandrei.orderservice.dto.book.BookDto;
import com.orlovandrei.orderservice.dto.internal.InternalUpdateAvailableCopiesRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service", url = "${application.config.books-url}")
public interface BookServiceClient {
    @GetMapping("/{id}")
    BookDto getBookById(@PathVariable("id") Long id);

    @PutMapping("/internal/{id}/available-copies")
    BookDto updateAvailableCopies(@PathVariable("id") Long id,
                                  @RequestBody InternalUpdateAvailableCopiesRequest request);
}

