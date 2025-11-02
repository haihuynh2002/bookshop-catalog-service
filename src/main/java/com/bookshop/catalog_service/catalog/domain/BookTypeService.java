package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.BookTypeResponse;
import com.bookshop.catalog_service.catalog.web.BookTypeUpdateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookTypeService {

    BookTypeRepository bookTypeRepository;
    BookTypeMapper bookTypeMapper;
    BookRepository bookRepository;
    TypeRepository typeRepository;

    public List<BookTypeResponse> getAllInventories() {
        return bookTypeRepository.findAll()
                .stream()
                .map(bookTypeMapper::toBookTypeResponse).toList();
    }

    public BookTypeResponse get(Long bookId, Long typeId) {
        return bookTypeRepository.findByBookIdAndTypeId(bookId, typeId)
                .map(bookTypeMapper::toBookTypeResponse)
                .orElseThrow(() -> new BookTypeNotFoundException(bookId, typeId));
    }

    public BookTypeResponse update(Long bookId, Long typeId, BookTypeUpdateRequest request) {
        BookType bookType = bookTypeRepository.findByBookIdAndTypeId(bookId, typeId)
                .orElseThrow(() -> new BookTypeNotFoundException(bookId, typeId));
        bookType.setQuantity(request.getQuantity());
        BookType updatedBookType = bookTypeRepository.save(bookType);
        return bookTypeMapper.toBookTypeResponse(updatedBookType);
    }
}
