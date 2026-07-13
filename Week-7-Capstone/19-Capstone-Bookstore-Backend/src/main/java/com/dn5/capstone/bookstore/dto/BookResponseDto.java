package com.dn5.capstone.bookstore.dto;

import java.math.BigDecimal;

/**
 * Response payload representing a {@code Book} to API clients.
 */
public class BookResponseDto {

    private Long id;
    private String title;
    private String isbn;
    private BigDecimal price;
    private Integer stockQuantity;
    private String authorName;
    private String categoryName;

    public BookResponseDto() {
    }

    public BookResponseDto(Long id, String title, String isbn, BigDecimal price, Integer stockQuantity,
                            String authorName, String categoryName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.authorName = authorName;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
