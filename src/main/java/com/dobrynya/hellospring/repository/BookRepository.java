package com.dobrynya.hellospring.repository;

import com.dobrynya.hellospring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Поиск по части названия (содержит)
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    // Поиск по автору (id)
    List<Book> findByAuthors_Id(Long authorId);

    // Поиск по имени автора
    List<Book> findByAuthors_NameContainingIgnoreCase(String authorName);

    // Найти книги, у которых есть тег с указанным id
    List<Book> findByTags_Id(Long tagId);

    // Найти книги, у которых есть тег с указанным именем
    List<Book> findByTags_NameContainingIgnoreCase(String tagName);
}
