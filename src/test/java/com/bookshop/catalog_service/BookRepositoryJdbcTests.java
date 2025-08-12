package com.bookshop.catalog_service;

import com.bookshop.catalog_service.config.DataConfig;
import com.bookshop.catalog_service.domain.Book;
import com.bookshop.catalog_service.domain.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@DataJpaTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllBooks() {
        var book1 = Book.of("1234561235", "Title", "Author", 12.90, "Polarsophia");
        var book2 = Book.of("1234561236", "Another Title", "Author", 12.90, "Polarsophia");
        entityManager.persist(book1);
        entityManager.persist(book2);

        Iterable<Book> actualBooks = bookRepository.findAll();

        Assertions.assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
                .filter(book -> book.getIsbn().equals(book1.getIsbn()) || book.getIsbn().equals(book2.getIsbn()))
                .collect(Collectors.toList())).hasSize(2);
    }

}
