package com.dn5.capstone.bookstore.mapper;

import com.dn5.capstone.bookstore.dto.BookRequestDto;
import com.dn5.capstone.bookstore.dto.BookResponseDto;
import com.dn5.capstone.bookstore.entity.Author;
import com.dn5.capstone.bookstore.entity.Book;
import com.dn5.capstone.bookstore.entity.Category;

/**
 * Static mapping helpers between {@code Book} entities and their DTOs.
 */
public final class BookMapper {

    private BookMapper() {
    }

    public static BookResponseDto toResponseDto(Book book) {
        if (book == null) {
            return null;
        }
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setStockQuantity(book.getStockQuantity());
        dto.setAuthorName(book.getAuthor() != null ? book.getAuthor().getName() : null);
        dto.setCategoryName(book.getCategory() != null ? book.getCategory().getName() : null);
        return dto;
    }

    public static Book toEntity(BookRequestDto dto, Author author, Category category) {
        if (dto == null) {
            return null;
        }
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setStockQuantity(dto.getStockQuantity());
        book.setAuthor(author);
        book.setCategory(category);
        return book;
    }

    public static void updateEntity(Book book, BookRequestDto dto, Author author, Category category) {
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setStockQuantity(dto.getStockQuantity());
        book.setAuthor(author);
        book.setCategory(category);
    }
}
