package com.bookshop.catalog_service.catalog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookTypeRepository extends JpaRepository<BookType, BookTypeId> {
    Optional<BookType> findByBookIdAndTypeId(Long bookId, Long typeId);
    List<BookType> findByBookId(Long bookId);
    List<BookType> findByTypeId(Long typeId);
}