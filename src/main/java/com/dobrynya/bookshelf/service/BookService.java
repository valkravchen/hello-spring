package com.dobrynya.bookshelf.service;

import com.dobrynya.bookshelf.dto.BookCreateDTO;
import com.dobrynya.bookshelf.dto.BookResponseDTO;
import com.dobrynya.bookshelf.exception.BookNotFoundException;
import com.dobrynya.bookshelf.mapper.BookMapper;
import com.dobrynya.bookshelf.model.Book;
import com.dobrynya.bookshelf.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final FileStorageService fileStorageService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper,
                       FileStorageService fileStorageService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.fileStorageService = fileStorageService;
    }

    public List<BookResponseDTO> findAll() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toResponseDTOList(books);
    }

    public BookResponseDTO findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toResponseDTO(book);
    }

    public BookResponseDTO save(BookCreateDTO dto) {
        Book book = bookMapper.toEntity(dto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    public BookResponseDTO update(Long id, BookCreateDTO dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        bookMapper.updateEntity(book, dto);
        Book updateBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(updateBook);
    }

    public List<BookResponseDTO> searchByTitle(String keyword) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword);
        return bookMapper.toResponseDTOList(books);
    }

    public List<BookResponseDTO> findByAuthorId(Long authorId) {
        List<Book> books = bookRepository.findByAuthors_Id(authorId);
        return bookMapper.toResponseDTOList(books);
    }

    public List<BookResponseDTO> findByAuthorName(String authorName) {
        List<Book> books = bookRepository.findByAuthors_NameContainingIgnoreCase(authorName);
        return bookMapper.toResponseDTOList(books);
    }

    public List<BookResponseDTO> findByTagId(Long tagId) {
        List<Book> books = bookRepository.findByTags_Id(tagId);
        return bookMapper.toResponseDTOList(books);
    }

    public List<BookResponseDTO> findByTagName(String tagName) {
        List<Book> books = bookRepository.findByTags_NameContainingIgnoreCase(tagName);
        return bookMapper.toResponseDTOList(books);
    }

    public void uploadPdf(Long bookId, MultipartFile file) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (book.getPdfPath() != null) {
            fileStorageService.deleteFile(book.getPdfPath());
        }

        String filePath = fileStorageService.saveFile(file, bookId);
        book.setPdfPath(filePath);
        bookRepository.save(book);
    }

    public byte[] getPdf(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        if (book.getPdfPath() != null) {
            throw new RuntimeException("У книги нет PDF файла");
        }
        return fileStorageService.getFile(book.getPdfPath());
    }

    public void deletePdf(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        if (book.getPdfPath() != null) {
            fileStorageService.deleteFile(book.getPdfPath());
            book.setPdfPath(null);
            bookRepository.save(book);
        }
    }
}
