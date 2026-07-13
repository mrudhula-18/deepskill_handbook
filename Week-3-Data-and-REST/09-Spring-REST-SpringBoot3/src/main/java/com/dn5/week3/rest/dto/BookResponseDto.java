package com.dn5.week3.rest.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

/**
 * Outbound representation of a {@code Book}, enriched with HATEOAS links
 * by the controller (self / collection links).
 */
public class BookResponseDto extends RepresentationModel<BookResponseDto> {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;

    public BookResponseDto() {
    }

    public BookResponseDto(Long id, String title, String author, String isbn, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
