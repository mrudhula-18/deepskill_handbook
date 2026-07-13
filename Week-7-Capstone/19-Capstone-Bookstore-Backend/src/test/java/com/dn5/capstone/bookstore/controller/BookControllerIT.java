package com.dn5.capstone.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dn5.capstone.bookstore.dto.BookRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end test hitting the real (H2 in-memory) database via MockMvc.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fullBookLifecycle_createReadUpdateDelete() throws Exception {
        // seed data.sql already provides author id 1 and category id 1
        BookRequestDto createRequest = new BookRequestDto(
                "Integration Test Book", "9781111111111", BigDecimal.valueOf(9.99), 5, 1L, 1L);

        String createResponse = mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Integration Test Book"))
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        Long newId = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(get("/api/v1/books/{id}", newId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("9781111111111"));

        BookRequestDto updateRequest = new BookRequestDto(
                "Integration Test Book Updated", "9781111111111", BigDecimal.valueOf(19.99), 8, 1L, 1L);

        mockMvc.perform(put("/api/v1/books/{id}", newId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Book Updated"))
                .andExpect(jsonPath("$.stockQuantity").value(8));

        mockMvc.perform(delete("/api/v1/books/{id}", newId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/books/{id}", newId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void listBooks_returnsSeededPage() throws Exception {
        mockMvc.perform(get("/api/v1/books").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(org.hamcrest.Matchers.greaterThanOrEqualTo(10)));
    }

    @Test
    void createBook_returnsBadRequest_whenValidationFails() throws Exception {
        BookRequestDto invalidRequest = new BookRequestDto("", "", BigDecimal.valueOf(-1), -5, null, null);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void searchBooks_returnsMatchingResults() throws Exception {
        mockMvc.perform(get("/api/v1/books/search").param("keyword", "Martian"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(org.hamcrest.Matchers.containsString("Martian")));
    }
}
