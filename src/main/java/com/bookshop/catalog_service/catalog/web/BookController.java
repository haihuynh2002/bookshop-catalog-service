package com.bookshop.catalog_service.catalog.web;

import com.bookshop.catalog_service.catalog.domain.BookService;
import com.bookshop.catalog_service.catalog.domain.Book;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {
    BookService bookService;

    @GetMapping
    public List<BookResponse> get() {
        return bookService.viewBookList();
    }

    @GetMapping("{id}")
    public BookResponse get(@PathVariable Long id) {
        return bookService.viewBookDetails(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(
            @Valid @ModelAttribute BookCreationRequest bookRequest,
            @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles
    ) {
        return bookService.addBookToCatalog(bookRequest, imageFiles);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.removeBookFromCatalog(id);
    }

    @PutMapping("{id}")
    public Book put( @Valid @ModelAttribute BookEditRequest bookRequest,
                     @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles,
                     @PathVariable Long id
    ) {
        return bookService.editBookDetails(id, bookRequest, imageFiles);
    }
}