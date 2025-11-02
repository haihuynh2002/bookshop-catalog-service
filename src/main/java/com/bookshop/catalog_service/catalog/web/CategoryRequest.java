package com.bookshop.catalog_service.catalog.web;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CategoryRequest {

    @NotBlank(message = "Category name must be defined.")
    String name;

    @NotBlank(message = "Category name must be defined.")
    String description;
}