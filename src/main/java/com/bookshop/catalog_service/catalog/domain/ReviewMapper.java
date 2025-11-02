package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.ReviewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toReview(ReviewRequest request);
    void updateReview(@MappingTarget Review review, ReviewRequest request);
}