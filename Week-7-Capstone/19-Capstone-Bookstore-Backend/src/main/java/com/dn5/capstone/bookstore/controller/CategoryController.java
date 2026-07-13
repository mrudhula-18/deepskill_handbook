package com.dn5.capstone.bookstore.controller;

import com.dn5.capstone.bookstore.entity.Category;
import com.dn5.capstone.bookstore.repository.CategoryRepository;
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
 * Simple CRUD-lite endpoints (list + create) so books can reference real categories.
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category saved = categoryRepository.save(new Category(request.name()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public record CategoryRequest(@NotBlank(message = "name must not be blank") String name) {
    }
}
