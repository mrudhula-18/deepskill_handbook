package com.dn5.capstone.bookstore.service.impl;

import com.dn5.capstone.bookstore.dto.BookRequestDto;
import com.dn5.capstone.bookstore.dto.BookResponseDto;
import com.dn5.capstone.bookstore.entity.Author;
import com.dn5.capstone.bookstore.entity.Book;
import com.dn5.capstone.bookstore.entity.Category;
import com.dn5.capstone.bookstore.exception.ResourceNotFoundException;
import com.dn5.capstone.bookstore.mapper.BookMapper;
import com.dn5.capstone.bookstore.repository.AuthorRepository;
import com.dn5.capstone.bookstore.repository.BookRepository;
import com.dn5.capstone.bookstore.repository.CategoryRepository;
import com.dn5.capstone.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository,
                            AuthorRepository authorRepository,
                            CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public BookResponseDto createBook(BookRequestDto requestDto) {
        Author author = findAuthorOrThrow(requestDto.getAuthorId());
        Category category = findCategoryOrThrow(requestDto.getCategoryId());
        Book book = BookMapper.toEntity(requestDto, author, category);
        Book saved = bookRepository.save(book);
        return BookMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book book = findBookOrThrow(id);
        return BookMapper.toResponseDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDto> listBooks(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return page.map(BookMapper::toResponseDto);
    }

    @Override
    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto requestDto) {
        Book book = findBookOrThrow(id);
        Author author = findAuthorOrThrow(requestDto.getAuthorId());
        Category category = findCategoryOrThrow(requestDto.getCategoryId());
        BookMapper.updateEntity(book, requestDto, author, category);
        Book saved = bookRepository.save(book);
        return BookMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = findBookOrThrow(id);
        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooks(String keyword) {
        return bookRepository.searchByTitleOrAuthorOrIsbn(keyword).stream()
                .map(BookMapper::toResponseDto)
                .toList();
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Book", id));
    }

    private Author findAuthorOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Author", id));
    }

    private Category findCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Category", id));
    }
}
