package com.orlovandrei.bookservice.service.impl;

import com.orlovandrei.bookservice.dto.book.BookDto;
import com.orlovandrei.bookservice.dto.book.CreateBookRequest;
import com.orlovandrei.bookservice.dto.book.UpdateBookRequest;
import com.orlovandrei.bookservice.dto.mapper.BookMapper;
import com.orlovandrei.bookservice.entity.Book;
import com.orlovandrei.bookservice.exception.BookAlreadyExistsException;
import com.orlovandrei.bookservice.exception.BookNotFoundException;
import com.orlovandrei.bookservice.exception.InvalidBookStateException;
import com.orlovandrei.bookservice.repository.BookRepository;
import com.orlovandrei.bookservice.service.BookService;
import com.orlovandrei.bookservice.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<BookDto> getAll(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size))
                .map(bookMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(Messages.BOOK_NOT_FOUND_BY_ID.getMessage() + id));
        return bookMapper.toDto(book);
    }

    @Transactional
    @Override
    public BookDto create(CreateBookRequest request) {
        if (bookRepository.existsByTitle((request.getTitle()))) {
            throw new BookAlreadyExistsException(Messages.BOOK_ALREADY_EXISTS.getMessage() + request.getTitle());
        }
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publicationYear(request.getPublicationYear())
                .genre(request.getGenre())
                .publisher(request.getPublisher())
                .availableCopies(request.getAvailableCopies())
                .totalCopies(request.getTotalCopies())
                .build();
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto update(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(Messages.BOOK_NOT_FOUND_BY_ID.getMessage() + id));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        book.setGenre(request.getGenre());
        book.setPublisher(request.getPublisher());
        book.setAvailableCopies(request.getAvailableCopies());
        book.setTotalCopies(request.getTotalCopies());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(Messages.BOOK_NOT_FOUND_BY_ID.getMessage() + id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public BookDto updateAvailableCopies(Long id, Integer availableCopies) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(Messages.BOOK_NOT_FOUND_BY_ID.getMessage() + id));

        if (availableCopies < 0) {
            throw new InvalidBookStateException(Messages.AVAILABLE_COPIES_CANNOT_BE_NEGATIVE.getMessage());
        }

        book.setAvailableCopies(availableCopies);
        return bookMapper.toDto(bookRepository.save(book));
    }

}



