package com.example.jpa.controller;

import com.example.jpa.domain.Book;
import com.example.jpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/ui")
public class RouteController {

    private final BookService bookService;

    @GetMapping(path = "/list")
    public String bookList(Model model) {

        List<Book> bookList = bookService.getList();
        model.addAttribute("bookList", bookList);

        return "book/list";
    }
}
