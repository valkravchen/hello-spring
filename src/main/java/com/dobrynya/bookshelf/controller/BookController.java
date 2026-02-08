package com.dobrynya.bookshelf.controller;

import com.dobrynya.bookshelf.dto.BookCreateDTO;
import com.dobrynya.bookshelf.dto.BookResponseDTO;
import com.dobrynya.bookshelf.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookCreateDTO dto) {
        BookResponseDTO savedBook = bookService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,
                                                      @Valid @RequestBody BookCreateDTO dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(
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

    @PostMapping("/{id}/pdf")
    public ResponseEntity<String> uploadPdf(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не выбран");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Только PDF файлы разрешены");
        }

        bookService.uploadPdf(id, file);
        return ResponseEntity.ok("PDF загружен успешно");
    }

    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        byte[] pdfContent = bookService.getPdf(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; " +
                        "filename=\"book_" + id + ".pdf\"")
                .body(pdfContent);
    }

    @DeleteMapping("/{id}/pdf")
    public ResponseEntity<Void> deletePdf(@PathVariable Long id) {
        bookService.deletePdf(id);
        return ResponseEntity.noContent().build();
    }
}
