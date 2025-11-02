package com.bookshop.catalog_service.catalog.domain;

public class BookTypeAlreadyExistsException extends RuntimeException{
    public BookTypeAlreadyExistsException(Long bookId, Long typeId) {
        super("A book type with  book id: " + bookId + " & type id: " + typeId  + " already exists.");
    }
}
