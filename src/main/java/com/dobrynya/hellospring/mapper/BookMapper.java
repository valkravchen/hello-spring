package com.dobrynya.hellospring.mapper;

import com.dobrynya.hellospring.dto.BookCreateDTO;
import com.dobrynya.hellospring.dto.BookResponseDTO;
import com.dobrynya.hellospring.model.Author;
import com.dobrynya.hellospring.model.Book;
import com.dobrynya.hellospring.model.Tag;
import com.dobrynya.hellospring.repository.AuthorRepository;
import com.dobrynya.hellospring.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                authorNames,
                tagNames
        );
    }

    public Book toEntity(BookCreateDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        // Найти авторов по id
        Set<Author> authors = new HashSet<>(
                authorRepository.findAllById(dto.getAuthorIds())
        );
        book.setAuthors(authors);
        // Найти теги по id (если есть)
        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(
                    tagRepository.findAllById(dto.getTagIds())
            );
            book.setTags(tags);
        }
        return book;
    }

    public List<BookResponseDTO> toResponseDTOList(List<Book> books) {
        return books.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void updateEntity(Book book, BookCreateDTO dto) {
        book.setTitle(dto.getTitle());
        Set<Author> authors = new HashSet<>(
                authorRepository.findAllById(dto.getAuthorIds())
        );
        book.setAuthors(authors);
        if (dto.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(
                    tagRepository.findAllById(dto.getTagIds())
            );
            book.setTags(tags);
        } else {
            book.setTags(new HashSet<>());
        }
    }
}
