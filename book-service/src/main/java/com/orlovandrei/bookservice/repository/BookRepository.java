package com.orlovandrei.bookservice.repository;

import com.orlovandrei.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitle(String title);
}
