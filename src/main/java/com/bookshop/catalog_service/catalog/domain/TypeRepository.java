package com.bookshop.catalog_service.catalog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Type> findByNameContainingIgnoreCase(String name);

    @Query("SELECT t FROM Type t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Type> findByDescriptionContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT t FROM Type t JOIN t.books bt WHERE bt.book.id = :bookId")
    List<Type> findByBookId(@Param("bookId") Long bookId);
}