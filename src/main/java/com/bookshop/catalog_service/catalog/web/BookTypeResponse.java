package com.bookshop.catalog_service.catalog.web;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookTypeResponse {
    Long bookId;
    String title;
    String author;
    String isbn;

    Long typeId;
    String name;

    Integer quantity;
}
