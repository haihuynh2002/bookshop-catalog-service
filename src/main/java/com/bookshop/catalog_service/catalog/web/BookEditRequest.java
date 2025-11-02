package com.bookshop.catalog_service.catalog.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookEditRequest {

    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    String isbn;

    @NotBlank(message = "The book title must be defined.")
    String title;

    @NotBlank(message = "The book author must be defined.")
    String author;

    String publisher;

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

    List<String> categories;

    List<String> types;
}