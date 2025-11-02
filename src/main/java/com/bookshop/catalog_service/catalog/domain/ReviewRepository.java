package com.bookshop.catalog_service.catalog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookId(Long bookId);
    Boolean existsByIdAndBookId(Long reviewId, Long bookId);

    @Transactional
    void deleteByBookId(Long bookId);

    @Transactional
    void deleteByIdAndBookId(Long id, Long bookId);

    Optional<Review> findByIdAndBookId(Long id, Long bookId);
}