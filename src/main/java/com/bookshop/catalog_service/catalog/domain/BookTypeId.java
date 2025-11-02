package com.bookshop.catalog_service.catalog.domain;

import java.io.Serializable;
import java.util.Objects;

public class BookTypeId implements Serializable {
    private Long book;
    private Long type;

    public BookTypeId() {}

    public BookTypeId(Long book, Long type) {
        this.book = book;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTypeId that = (BookTypeId) o;
        return Objects.equals(book, that.book) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, type);
    }
}