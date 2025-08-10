package com.orlovandrei.bookservice.service;

import com.orlovandrei.bookservice.dto.book.BookDto;
import com.orlovandrei.bookservice.dto.book.CreateBookRequest;
import com.orlovandrei.bookservice.dto.book.UpdateBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface BookService {
    @Transactional(readOnly = true)
    Page<BookDto> getAll(int page, int size);

    @Transactional(readOnly = true)
    BookDto getById(Long id);

    @Transactional
    BookDto create(CreateBookRequest request);

    @Transactional
    BookDto update(Long id, UpdateBookRequest request);

    @Transactional
    void delete(Long id);

    @Transactional
    BookDto updateAvailableCopies(Long id, Integer availableCopies);
}
