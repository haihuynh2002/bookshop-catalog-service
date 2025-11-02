package com.bookshop.catalog_service.catalog.domain;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String isbn) {
        super("The book with ISBN " + isbn + " was not found.");
    }
}