package com.example.jpa.controller;

import com.example.jpa.domain.Book;
import com.example.jpa.repository.BookRepository;
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
    private final BookRepository bookRepository;

    @GetMapping(path = "/book")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(bookService.getList(), HttpStatus.OK);
    }

    @PostMapping(path = "/book")
    public ResponseEntity<?> save(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.register(book), HttpStatus.CREATED);
    }

    @GetMapping(path = "/book/price/{price}")
    public ResponseEntity<?> findByPrice(@PathVariable Integer price) {
        return new ResponseEntity<>(bookRepository.findByPrice(price), HttpStatus.OK);
    }

    @GetMapping(path = "/book/title/{title}/author/{author}")
    public ResponseEntity<?> findByTitleAndAuthor(@PathVariable String title,
                                                  @PathVariable String author) {
        return new ResponseEntity<>(bookService.findByTitleAndAuthor(title, author), HttpStatus.OK);
    }
}
