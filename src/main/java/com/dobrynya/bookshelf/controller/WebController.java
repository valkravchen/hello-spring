package com.dobrynya.bookshelf.controller;

import com.dobrynya.bookshelf.dto.BookCreateDTO;
import com.dobrynya.bookshelf.dto.BookResponseDTO;
import com.dobrynya.bookshelf.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class WebController {
    private final BookService bookService;

    public WebController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "BookShelf");
        model.addAttribute("message", "Добро пожаловать в библиотеку!");
        model.addAttribute("bookCount", bookService.findAll().size());
        model.addAttribute("currentDate", LocalDate.now());
        return "index";
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        List<BookResponseDTO> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/books/new")
    public String showCreateForm() {
        return "book-form";
    }

    @PostMapping("/books")
    public String createBook(
            @RequestParam String title,
            @RequestParam String authorNames,
            @RequestParam(required = false) String tagNames
    ) {
        Set<String> authorSet = Arrays.stream(authorNames.split(","))
                .map(String::trim)
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toSet());

        Set<String> tagSet = null;

        if (tagNames != null && !tagNames.isBlank()) {
            tagSet = Arrays.stream(tagNames.split(","))
                    .map(String::trim)
                    .filter(string -> !string.isEmpty())
                    .collect(Collectors.toSet());
        }

        BookCreateDTO dto = new BookCreateDTO();
        dto.setTitle(title);
        dto.setAuthorNames(authorSet);
        dto.setTagNames(tagSet);

        bookService.save(dto);

        return "redirect:/books";
    }

    @PostMapping("/book/{id}delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
