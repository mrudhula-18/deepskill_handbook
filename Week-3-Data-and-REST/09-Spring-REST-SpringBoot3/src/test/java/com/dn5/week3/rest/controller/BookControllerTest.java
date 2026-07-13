package com.dn5.week3.rest.controller;

import java.math.BigDecimal;

import com.dn5.week3.rest.dto.BookRequestDto;
import com.dn5.week3.rest.entity.Book;
import com.dn5.week3.rest.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end MockMvc test for {@link BookController}.
 *
 * Security note: the real API requires a bearer token obtained from
 * {@code POST /api/auth/login} (username "admin" / password "admin123",
 * see {@link com.dn5.week3.rest.controller.AuthController}) on every
 * endpoint except the whitelisted ones in
 * {@link com.dn5.week3.rest.config.SecurityConfig}. To keep this test
 * focused on the controller/repository behaviour rather than on token
 * plumbing, we take the simpler route and disable the security filter
 * chain for this test with {@code @AutoConfigureMockMvc(addFilters = false)}.
 * The equivalent "real" flow would be:
 *
 * <pre>
 *   String token = objectMapper.readTree(
 *       mockMvc.perform(post("/api/auth/login")
 *               .contentType(MediaType.APPLICATION_JSON)
 *               .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
 *           .andReturn().getResponse().getContentAsString()).get("token").asText();
 *
 *   mockMvc.perform(get("/api/books").header("Authorization", "Bearer " + token));
 * </pre>
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook_returns201WithLocationAndSelfLink() throws Exception {
        BookRequestDto request = new BookRequestDto("Effective Java", "Joshua Bloch", "978-0134685991",
                new BigDecimal("45.00"));

        mockMvc.perform(post("/api/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.title", is("Effective Java")))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void createBook_withBlankTitle_returns400WithFieldErrors() throws Exception {
        BookRequestDto request = new BookRequestDto("", "Someone", "111-1", new BigDecimal("10.00"));

        mockMvc.perform(post("/api/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.title").exists());
    }

    @Test
    void getBook_whenExists_returns200WithSelfAndCollectionLinks() throws Exception {
        Book saved = bookRepository.save(new Book("Clean Code", "Robert C. Martin", "978-0132350884",
                new BigDecimal("38.20")));

        mockMvc.perform(get("/api/books/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Clean Code")))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.collection.href").exists());
    }

    @Test
    void getBook_whenMissing_returns404WithErrorBody() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 999_999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void listBooks_returnsCollectionWithSelfLink() throws Exception {
        bookRepository.save(new Book("Refactoring", "Martin Fowler", "978-0134757599", new BigDecimal("47.99")));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._embedded").exists());
    }

    @Test
    void deleteBook_whenExists_returns204() throws Exception {
        Book saved = bookRepository.save(new Book("Domain-Driven Design", "Eric Evans", "978-0321125217",
                new BigDecimal("54.99")));

        mockMvc.perform(delete("/api/books/{id}", saved.getId()))
                .andExpect(status().isNoContent());
    }
}
