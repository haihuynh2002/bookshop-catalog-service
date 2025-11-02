package com.bookshop.catalog_service.catalog.web;

import com.bookshop.catalog_service.catalog.domain.BookType;
import com.bookshop.catalog_service.catalog.domain.Category;
import com.bookshop.catalog_service.catalog.domain.Review;
import com.bookshop.catalog_service.file.FileResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookResponse {
    Long id;
    String isbn;
    String title;
    String author;
    String publisher;
    String description;
    Double price;
    Double weight;
    Double height;
    Double width;
    Boolean enable;

    Set<Category> categories;

    Set<Review> reviews;

    Set<FileResponse> images;

    Set<BookTypeResponse> bookTypes;

    Instant createdDate;

    Instant lastModifiedDate;

    String createdBy;

    String lastModifiedBy;
}