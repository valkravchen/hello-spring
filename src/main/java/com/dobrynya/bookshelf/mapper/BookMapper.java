package com.dobrynya.bookshelf.mapper;

import com.dobrynya.bookshelf.dto.BookCreateDTO;
import com.dobrynya.bookshelf.dto.BookResponseDTO;
import com.dobrynya.bookshelf.model.Author;
import com.dobrynya.bookshelf.model.Book;
import com.dobrynya.bookshelf.model.Tag;
import com.dobrynya.bookshelf.repository.AuthorRepository;
import com.dobrynya.bookshelf.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;

    public BookMapper(AuthorRepository authorRepository, TagRepository tagRepository) {
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    // Entity → ResponseDTO
    public BookResponseDTO toResponseDTO(Book book) {
        List<String> authorNames = book.getAuthors().stream()
                .map(Author::getName)
                .toList();

        List<String> tagNames = book.getTags().stream()
                .map(Tag::getName)
                .toList();

        boolean hasPdf = book.getPdfPath() != null;

        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                authorNames,
                tagNames,
                hasPdf
        );
    }

    public List<BookResponseDTO> toResponseDTOList(List<Book> books) {
        return books.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Book toEntity(BookCreateDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());

        // Найти или создать авторов
        Set<Author> authors = dto.getAuthorNames().stream()
                .map(this::findOrCreateAuthor)
                .collect(Collectors.toSet());
        book.setAuthors(authors);

        // Найти или создать теги
        if (dto.getTagNames() != null && !dto.getTagNames().isEmpty()) {
            Set<Tag> tags = dto.getTagNames().stream()
                    .map(this::findOrCreateTag)
                    .collect(Collectors.toSet());
            book.setTags(tags);
        }
        return book;
    }

    public void updateEntity(Book book, BookCreateDTO dto) {
        book.setTitle(dto.getTitle());

        // Найти или создать авторов
        Set<Author> authors = dto.getAuthorNames().stream()
                .map(this::findOrCreateAuthor)
                .collect(Collectors.toSet());
        book.setAuthors(authors);

        // Найти или создать теги
        if (dto.getTagNames() != null && !dto.getTagNames().isEmpty()) {
            Set<Tag> tags = dto.getTagNames().stream()
                    .map(this::findOrCreateTag)
                    .collect(Collectors.toSet());
            book.setTags(tags);
        } else {
            book.setTags(new HashSet<>());
        }
    }

    private Author findOrCreateAuthor(String name) {
        return authorRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> authorRepository.save(new Author(name)));
    }

    private Tag findOrCreateTag(String name) {
        return tagRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> tagRepository.save(new Tag(name)));
    }
}
