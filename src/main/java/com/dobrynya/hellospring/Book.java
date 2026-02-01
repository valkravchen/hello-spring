package com.dobrynya.hellospring;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private Long id;
    private String title;

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    public static Book findBook(List<Book> books, Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Spring in Action"));
        books.add(new Book(2L, "Effective Java"));
        System.out.println(findBook(books, 1L));
        System.out.println(findBook(books, 99L));
    }
}

class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id) {
        super("Книга с id " + id + " не найдена");
    }
}

