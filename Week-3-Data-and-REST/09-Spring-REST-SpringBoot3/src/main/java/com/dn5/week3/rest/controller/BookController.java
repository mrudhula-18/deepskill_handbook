package com.dn5.week3.rest.controller;

import java.net.URI;
import java.util.List;

import com.dn5.week3.rest.dto.BookMapper;
import com.dn5.week3.rest.dto.BookRequestDto;
import com.dn5.week3.rest.dto.BookResponseDto;
import com.dn5.week3.rest.entity.Book;
import com.dn5.week3.rest.exception.ResourceNotFoundException;
import com.dn5.week3.rest.repository.BookRepository;

import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<BookResponseDto>> listBooks() {
        List<BookResponseDto> books = bookRepository.findAll().stream()
                .map(this::toResponseWithSelfLink)
                .toList();

        Link selfLink = linkTo(methodOn(BookController.class).listBooks()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(books, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        Book book = findBookOrThrow(id);
        BookResponseDto dto = BookMapper.toResponseDto(book);
        dto.add(linkTo(methodOn(BookController.class).getBook(id)).withSelfRel());
        dto.add(linkTo(methodOn(BookController.class).listBooks()).withRel("collection"));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto request) {
        Book saved = bookRepository.save(BookMapper.toEntity(request));
        BookResponseDto dto = BookMapper.toResponseDto(saved);
        dto.add(linkTo(methodOn(BookController.class).getBook(saved.getId())).withSelfRel());
        dto.add(linkTo(methodOn(BookController.class).listBooks()).withRel("collection"));

        URI location = linkTo(methodOn(BookController.class).getBook(saved.getId())).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id,
                                                       @Valid @RequestBody BookRequestDto request) {
        Book book = findBookOrThrow(id);
        BookMapper.updateEntity(book, request);
        Book saved = bookRepository.save(book);

        BookResponseDto dto = BookMapper.toResponseDto(saved);
        dto.add(linkTo(methodOn(BookController.class).getBook(id)).withSelfRel());
        dto.add(linkTo(methodOn(BookController.class).listBooks()).withRel("collection"));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = findBookOrThrow(id);
        bookRepository.delete(book);
        return ResponseEntity.noContent().build();
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    private BookResponseDto toResponseWithSelfLink(Book book) {
        BookResponseDto dto = BookMapper.toResponseDto(book);
        dto.add(linkTo(methodOn(BookController.class).getBook(book.getId())).withSelfRel());
        return dto;
    }
}
