package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.BookTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookTypeMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.isbn", target = "isbn")
    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "name")
    BookTypeResponse toBookTypeResponse(BookType bookType);
}
