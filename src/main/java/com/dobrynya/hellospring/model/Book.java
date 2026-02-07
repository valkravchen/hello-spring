package com.dobrynya.hellospring.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({"id", "title", "author"})
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название обязательно")
    @Size(min = 2, max = 200, message = "Название: от 2 до 200 символов")
    private String title;

    @NotEmpty(message = "Укажите хотя бы одного автора")
    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable( // описывает промежуточную таблицу
            name = "book_tags", // name - имя таблицы
            joinColumns = @JoinColumn(name = "book_id"), // колонка для текущей сущности (Book → book_id)
            inverseJoinColumns = @JoinColumn(name = "tag_id") // колонка для связанной сущности (Tag → tag_id)
    )
    private Set<Tag> tags = new HashSet<>();

    public Book() {
    }

    public Book(String title, Set<Author> authors) {
        this.title = title;
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
