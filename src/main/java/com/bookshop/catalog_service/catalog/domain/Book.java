package com.bookshop.catalog_service.catalog.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    String isbn;

    @NotBlank(message = "The book title must be defined.")
    String title;

    @NotBlank(message = "The book author must be defined.")
    String author;

    String publisher;

    @NotBlank(message = "Description cannot be blank")
    String description;

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    BigDecimal price;

    @NotNull(message = "The book weight must be defined.")
    @Positive(message = "The book weight must be greater than zero.")
    BigDecimal weight;

    @NotNull(message = "The book height must be defined.")
    @Positive(message = "The book height must be greater than zero.")
    BigDecimal height;

    @NotNull(message = "The book width must be defined.")
    @Positive(message = "The book width must be greater than zero.")
    BigDecimal width;

    @NotNull(message = "The activation must be defined.")
    Boolean enable;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    Set<BookType> bookTypes = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Review> reviews = new HashSet<>();

    @CreatedDate
    Instant createdDate;

    @LastModifiedDate
    Instant lastModifiedDate;

    @CreatedBy
    String createdBy;

    @LastModifiedBy
    String lastModifiedBy;

    @Version
    int version;

    // Helper methods for managing types
    public void addType(Type type, Integer quantity) {
        BookType bookType = BookType.builder()
                .book(this)
                .type(type)
                .quantity(quantity)
                .build();
        bookTypes.add(bookType);
        type.getBooks().add(bookType);
    }

    public void removeType(Type type) {
        BookType bookType = new BookType(this, type, 0);
        bookTypes.remove(bookType);
        type.getBooks().remove(bookType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return version == book.version &&
                id.equals(book.id) &&
                isbn.equals(book.isbn) &&
                title.equals(book.title) &&
                author.equals(book.author) &&
                price.equals(book.price) &&
                Objects.equals(publisher, book.publisher) &&
                createdDate.equals(book.createdDate) &&
                lastModifiedDate.equals(book.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, author, price, publisher, createdDate, lastModifiedDate, version);
    }
}