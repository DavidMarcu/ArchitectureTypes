package com.dmarcu.hexagonal.domain.adapters;

import com.dmarcu.hexagonal.domain.Book;
import com.dmarcu.hexagonal.domain.ports.BookRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryAdapter implements BookRepositoryPort {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryAdapter(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String addBook(Book book) {
        int rowsAffected = jdbcTemplate.update("INSERT INTO books(isbn, title, authors) VALUES(?, ?, ?)",
                book.getIsbn(), book.getTitle(), book.getAuthors());
        if(rowsAffected == 1){
            return book.getIsbn();
        }
        return "";
    }
}
