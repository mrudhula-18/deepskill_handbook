package com.dn5.capstone.bookstore.service;

import com.dn5.capstone.bookstore.dto.BookRequestDto;
import com.dn5.capstone.bookstore.dto.BookResponseDto;
import com.dn5.capstone.bookstore.entity.Author;
import com.dn5.capstone.bookstore.entity.Book;
import com.dn5.capstone.bookstore.entity.Category;
import com.dn5.capstone.bookstore.exception.ResourceNotFoundException;
import com.dn5.capstone.bookstore.repository.AuthorRepository;
import com.dn5.capstone.bookstore.repository.BookRepository;
import com.dn5.capstone.bookstore.repository.CategoryRepository;
import com.dn5.capstone.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Author author;
    private Category category;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("Andy Weir");
        author.setId(1L);

        category = new Category("Science Fiction");
        category.setId(1L);

        book = new Book("The Martian", "9780553418026", BigDecimal.valueOf(12.99), 50, author, category);
        book.setId(1L);
    }

    @Test
    void createBook_savesAndReturnsDto_whenAuthorAndCategoryExist() {
        BookRequestDto request = new BookRequestDto("The Martian", "9780553418026",
                BigDecimal.valueOf(12.99), 50, 1L, 1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponseDto result = bookService.createBook(request);

        assertThat(result.getTitle()).isEqualTo("The Martian");
        assertThat(result.getAuthorName()).isEqualTo("Andy Weir");
        assertThat(result.getCategoryName()).isEqualTo("Science Fiction");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_throwsResourceNotFound_whenAuthorMissing() {
        BookRequestDto request = new BookRequestDto("The Martian", "9780553418026",
                BigDecimal.valueOf(12.99), 50, 99L, 1L);

        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.createBook(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Author");

        verify(bookRepository, never()).save(any());
    }

    @Test
    void getBook_returnsDto_whenFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponseDto result = bookService.getBook(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getIsbn()).isEqualTo("9780553418026");
    }

    @Test
    void getBook_throwsResourceNotFound_whenMissing() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBook(404L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book not found with id: 404");
    }

    @Test
    void deleteBook_deletes_whenFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void searchBooks_returnsMappedResults() {
        when(bookRepository.searchByTitleOrAuthorOrIsbn("martian")).thenReturn(List.of(book));

        List<BookResponseDto> results = bookService.searchBooks("martian");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("The Martian");
    }
}
