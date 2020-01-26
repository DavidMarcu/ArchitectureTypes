package com.dmarcu.hexagonal.infrastructure;

import com.dmarcu.hexagonal.domain.Book;
import com.dmarcu.hexagonal.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO books(isbn, title, author) VALUES(?, ?, ?)",
                book.getIsbn(), book.getTitle(), book.getAuthor());
    }
}
