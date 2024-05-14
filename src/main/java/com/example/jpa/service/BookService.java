package com.example.jpa.service;

import com.example.jpa.domain.Book;
import com.example.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getList() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book register(Book book) {
        return bookRepository.save(book);
    }
}
