package com.bookshop.catalog_service;

import com.bookshop.catalog_service.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	void whenPostRequestThenBookCreated() {
		var bookIsbn = "1231231230";
		var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, "Polarsophia");

		Book expectedBook = webTestClient.post()
				.uri("/books")
				.bodyValue(bookToCreate)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(book -> Assertions.assertThat(book).isNotNull())
				.returnResult().getResponseBody();

		webTestClient.get()
				.uri("/books/" + bookIsbn)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Book.class).value(book -> {
					Assertions.assertThat(book).isNotNull();
					Assertions.assertThat(book.getIsbn()).isEqualTo(bookIsbn);
				});
	}

}
