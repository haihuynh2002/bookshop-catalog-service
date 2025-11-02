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
public class BookTypeRequest {

    @NotNull(message = "Type ID must be defined.")
    private Long typeId;

    @NotNull(message = "Quantity must be defined.")
    @PositiveOrZero(message = "Quantity must be zero or positive.")
    private Integer quantity;
}