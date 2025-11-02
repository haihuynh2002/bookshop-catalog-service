package com.bookshop.catalog_service.catalog.domain;

public class CategoryAlreadyExistsException extends RuntimeException{
    public CategoryAlreadyExistsException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}
