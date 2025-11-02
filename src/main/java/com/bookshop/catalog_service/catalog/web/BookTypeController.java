package com.bookshop.catalog_service.catalog.web;

import com.bookshop.catalog_service.catalog.domain.BookType;
import com.bookshop.catalog_service.catalog.domain.BookTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("inventories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookTypeController {

    BookTypeService bookTypeService;

    @GetMapping
    public List<BookTypeResponse> getAll() {
        return bookTypeService.getAllInventories();
    }

    @GetMapping("/book/{bookId}/type/{typeId}")
    public BookTypeResponse getInventoryItem(
            @PathVariable Long bookId,
            @PathVariable Long typeId) {
        return bookTypeService.get(bookId, typeId);
    }

    @PutMapping("/book/{bookId}/type/{typeId}")
    public BookTypeResponse updateInventory(
            @PathVariable Long bookId,
            @PathVariable Long typeId,
            @Valid @RequestBody BookTypeUpdateRequest request) {
        return bookTypeService.update(bookId, typeId, request);
    }
}
