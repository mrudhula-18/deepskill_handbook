package com.dn5.capstone.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * Request payload used to create or update a {@code Book}.
 */
public class BookRequestDto {

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "isbn must not be blank")
    private String isbn;

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "stockQuantity is required")
    @PositiveOrZero(message = "stockQuantity must be zero or greater")
    private Integer stockQuantity;

    @NotNull(message = "authorId is required")
    private Long authorId;

    @NotNull(message = "categoryId is required")
    private Long categoryId;

    public BookRequestDto() {
    }

    public BookRequestDto(String title, String isbn, BigDecimal price, Integer stockQuantity, Long authorId, Long categoryId) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
