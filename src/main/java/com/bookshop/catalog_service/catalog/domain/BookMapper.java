package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.BookCreationRequest;
import com.bookshop.catalog_service.catalog.web.BookEditRequest;
import com.bookshop.catalog_service.catalog.web.BookResponse;
import com.bookshop.catalog_service.catalog.web.BookTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper {
    @Mapping(target = "categories", ignore = true)
    Book toBook(BookCreationRequest request);

    BookResponse toBookResponse(Book book);

    @Mapping(target = "categories", ignore = true)
    void updateBook(@MappingTarget Book book, BookEditRequest request);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.isbn", target = "isbn")
    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "name")
    BookTypeResponse toBookTypeResponse(BookType bookType);
}