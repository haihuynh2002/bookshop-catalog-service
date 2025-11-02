package com.bookshop.catalog_service.catalog.demo;

import java.math.BigDecimal;
import java.util.List;


import com.bookshop.catalog_service.catalog.domain.Book;
import com.bookshop.catalog_service.catalog.domain.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookDataLoader {

    BookRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
//        bookRepository.deleteAll();
//        var book1 = Book.builder()
//                .isbn("1234567891")
//                .title("Northern Lights")
//                .author("Lyra Silvertongue")
//                .price(BigDecimal.valueOf(9.90))
//                .description("12")
//                .publisher("Polarsophia")
//                .enable(true)
//                .build();
//        var book2 = Book.builder()
//                .isbn("1234567892")
//                .title("Polar Journey")
//                .author("Iorek Polarson")
//                .price(BigDecimal.valueOf(12.90))
//                .description("12")
//                .publisher("Polarsophia")
//                .enable(true)
//                .build();
//        bookRepository.saveAll(List.of(book1, book2));
    }

}