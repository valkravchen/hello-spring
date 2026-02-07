package com.dobrynya.hellospring.controller;

import com.dobrynya.hellospring.model.Book;
import com.dobrynya.hellospring.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody Book book) {
        Book updateBook = bookService.update(id, book);
        return ResponseEntity.ok(updateBook);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) String tagName
    ) {
        // - Если передан title → искать по title
        if (title != null) {
            return ResponseEntity.ok(bookService.searchByTitle(title));
        }
        // - Если передан authorId → искать по authorId
        if (authorId != null) {
            return ResponseEntity.ok(bookService.findByAuthorId(authorId));
        }
        // - Если передан authorName → искать по authorName
        if (authorName != null) {
            return ResponseEntity.ok(bookService.findByAuthorName(authorName));
        }

        if (tagId != null) {
            return ResponseEntity.ok(bookService.findByTagId(tagId));
        }

        if (tagName != null) {
            return ResponseEntity.ok(bookService.findByTagName(tagName));
        }
        // - Если ничего не передано → вернуть все книги
        return ResponseEntity.ok(bookService.findAll());
    }
}
