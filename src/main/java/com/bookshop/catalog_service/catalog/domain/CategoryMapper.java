package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}