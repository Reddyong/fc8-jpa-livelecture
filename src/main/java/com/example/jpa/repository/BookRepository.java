package com.example.jpa.repository;

import com.example.jpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    public Book findByTitleAndAuthor(String title, String author);

    @Query("select b from Book b where b.title = :title and b.author = :author")
    public Book findByTitleAndAuthorName(@Param("title") String title,
                                         @Param("author") String author);

//    @Query("select b from Book b where b.price > :price")
    @Query(value = "select * from book b where b.title > ?1", nativeQuery = true)
    public List<Book> findByPrice(@Param("price") Integer price);
}
