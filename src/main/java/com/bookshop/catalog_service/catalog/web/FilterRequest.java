package com.bookshop.catalog_service.catalog.web;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FilterRequest {
    List<Long> categoryIds;
    List<String> types;
    BigDecimal minPrice;
    BigDecimal maxPrice;
}
