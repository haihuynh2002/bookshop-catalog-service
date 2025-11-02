package com.bookshop.catalog_service.catalog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "book_type")
@IdClass(BookTypeId.class)
public class BookType {

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "type_id")
    Type type;

    @NotNull(message = "Quantity must be defined.")
    @PositiveOrZero(message = "Quantity must be zero or positive.")
    Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookType bookType = (BookType) o;
        return book.equals(bookType.book) &&
                type.equals(bookType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, type);
    }
}