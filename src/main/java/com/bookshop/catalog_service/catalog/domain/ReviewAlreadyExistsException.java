package com.bookshop.catalog_service.catalog.domain;

public class ReviewAlreadyExistsException extends RuntimeException{
    public ReviewAlreadyExistsException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}
