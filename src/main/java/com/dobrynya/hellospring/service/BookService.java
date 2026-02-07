package com.dobrynya.hellospring.service;

import com.dobrynya.hellospring.exception.BookNotFoundException;
import com.dobrynya.hellospring.model.Book;
import com.dobrynya.hellospring.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    public Book update(Long id, Book updateBook) {
        Book book = findById(id);
        book.setTitle(updateBook.getTitle());
        book.setAuthor(updateBook.getAuthor());
        book.setTags(updateBook.getTags());
        return bookRepository.save(book);
    }

    public List<Book> searchByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Book> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public List<Book> findByAuthorName(String authorName) {
        return bookRepository.findByAuthorNameContainingIgnoreCase(authorName);
    }
}
