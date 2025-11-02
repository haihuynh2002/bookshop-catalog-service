package com.bookshop.catalog_service.catalog.domain;

public class BookTypeNotFoundException extends RuntimeException {
    public BookTypeNotFoundException(Long bookId, Long typeId) {
        super("BookType relationship not found for bookId: " + bookId + " and typeId: " + typeId);
    }
}