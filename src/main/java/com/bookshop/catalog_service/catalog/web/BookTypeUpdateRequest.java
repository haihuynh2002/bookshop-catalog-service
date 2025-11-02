package com.bookshop.catalog_service.catalog.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookTypeUpdateRequest {

    @NotNull(message = "Quantity must be defined.")
    @PositiveOrZero(message = "Quantity must be zero or positive.")
    private Integer quantity;
}