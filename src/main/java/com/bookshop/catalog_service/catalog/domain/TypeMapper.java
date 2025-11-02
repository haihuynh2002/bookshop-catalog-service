package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.TypeRequest;
import com.bookshop.catalog_service.catalog.web.TypeResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Type toType(TypeRequest typeRequest);

    TypeResponse toTypeResponse(Type type);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTypeFromRequest(TypeRequest typeRequest, @MappingTarget Type type);
}