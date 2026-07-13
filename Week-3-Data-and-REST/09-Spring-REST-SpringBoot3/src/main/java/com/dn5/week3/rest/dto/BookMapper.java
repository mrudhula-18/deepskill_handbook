package com.dn5.week3.rest.dto;

import com.dn5.week3.rest.entity.Book;

/**
 * Plain static mapper between {@link Book} and its request/response DTOs.
 * No MapStruct dependency needed for a mapping this small.
 */
public final class BookMapper {

    private BookMapper() {
    }

    public static Book toEntity(BookRequestDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        return book;
    }

    public static void updateEntity(Book book, BookRequestDto dto) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
    }

    public static BookResponseDto toResponseDto(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice());
    }
}
