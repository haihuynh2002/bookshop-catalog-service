package com.bookshop.catalog_service.web;

import com.bookshop.catalog_service.config.SecurityConfig;
import com.bookshop.catalog_service.domain.BookNotFoundException;
import com.bookshop.catalog_service.domain.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
public class BookControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    JwtDecoder jwtDecoder;

    @Test
    void whenDeleteBookWithEmployeeRoleThenShouldReturn204() throws Exception {
        var isbn = "7373731394";
        mockMvc
          .perform(MockMvcRequestBuilders.delete("/books/" + isbn)
            .with(SecurityMockMvcRequestPostProcessors.jwt()
                    .authorities(new SimpleGrantedAuthority("ROLE_employee"))))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

//    @Test
//    void whenDeleteBookWithCustomerRoleThenShouldReturn403()
//            throws Exception
//    {
//        var isbn = "7373731394";
//        mockMvc
//      .perform(MockMvcRequestBuilders.delete("/books/" + isbn)
//            .with(SecurityMockMvcRequestPostProcessors.jwt()
//                    .authorities(new SimpleGrantedAuthority("ROLE_customer"))))
//            .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    void whenDeleteBookNotAuthenticatedThenShouldReturn401()
//            throws Exception
//    {
//        var isbn = "7373731394";
//        mockMvc
//                .perform(MockMvcRequestBuilders.delete("/books/" + isbn))
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }
//
//    @Test
//    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
//        String isbn = "123";
//        BDDMockito.given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);
//        mockMvc.perform(MockMvcRequestBuilders.get("/book/" + isbn))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }
}
