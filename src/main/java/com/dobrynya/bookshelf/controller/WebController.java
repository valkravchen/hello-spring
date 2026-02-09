package com.dobrynya.bookshelf.controller;

import com.dobrynya.bookshelf.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

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
}
