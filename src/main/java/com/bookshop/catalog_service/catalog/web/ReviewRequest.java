package com.bookshop.catalog_service.catalog.web;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReviewRequest {

    @NotBlank(message = "Review content must be defined.")
    @Size(max = 1000, message = "Review content must not exceed 1000 characters.")
    String content;

    @NotNull(message = "Rating must be defined.")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating;

    @NotBlank(message = "Reviewer name must be defined.")
    String title;
}