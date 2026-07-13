package com.dn5.capstone.bookstore.controller;

import com.dn5.capstone.bookstore.entity.Author;
import com.dn5.capstone.bookstore.repository.AuthorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Simple CRUD-lite endpoints (list + create) so books can reference real authors.
 */
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Author>> listAuthors() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody AuthorRequest request) {
        Author saved = authorRepository.save(new Author(request.name()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public record AuthorRequest(@NotBlank(message = "name must not be blank") String name) {
    }
}
