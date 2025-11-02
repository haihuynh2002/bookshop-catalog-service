package com.bookshop.catalog_service.catalog.domain;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String id) {
        super("The category with id " + id + " was not found.");
    }
}