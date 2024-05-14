package com.example.jpa.controller;

import com.example.jpa.domain.Book;
import com.example.jpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class BookController {

    private final BookService bookService;

    @GetMapping(path = "/book")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(bookService.getList(), HttpStatus.OK);
    }

    @PostMapping(path = "/book")
    public ResponseEntity<?> save(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.register(book), HttpStatus.CREATED);
    }
}
