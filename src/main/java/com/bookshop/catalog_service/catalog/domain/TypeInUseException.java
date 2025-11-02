package com.bookshop.catalog_service.catalog.domain;

public class TypeInUseException extends RuntimeException {
    public TypeInUseException(Long typeId, int bookCount) {
        super("Type with id " + typeId + " is associated with " + bookCount + " books and cannot be deleted");
    }
}