package com.orlovandrei.bookservice.dto.mapper;

import com.orlovandrei.bookservice.dto.book.BookDto;
import com.orlovandrei.bookservice.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);
    Book toEntity(BookDto dto);
}