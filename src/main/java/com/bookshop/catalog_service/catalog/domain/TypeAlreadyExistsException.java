package com.bookshop.catalog_service.catalog.domain;

public class TypeAlreadyExistsException extends RuntimeException {
    public TypeAlreadyExistsException(String name) {
        super("Type with name '" + name + "' already exists");
    }
}