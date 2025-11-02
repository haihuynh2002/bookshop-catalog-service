package com.bookshop.catalog_service.catalog.domain;

public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException(Long id) {
        super("Type with id " + id + " not found");
    }

    public TypeNotFoundException(String message) {
        super("Type " + message + " not found");
    }
}