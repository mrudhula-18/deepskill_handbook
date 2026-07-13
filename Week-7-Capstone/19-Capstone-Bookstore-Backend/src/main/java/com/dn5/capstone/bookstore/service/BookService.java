package com.dn5.capstone.bookstore.service;

import com.dn5.capstone.bookstore.dto.BookRequestDto;
import com.dn5.capstone.bookstore.dto.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(BookRequestDto requestDto);

    BookResponseDto getBook(Long id);

    Page<BookResponseDto> listBooks(Pageable pageable);

    BookResponseDto updateBook(Long id, BookRequestDto requestDto);

    void deleteBook(Long id);

    List<BookResponseDto> searchBooks(String keyword);
}
