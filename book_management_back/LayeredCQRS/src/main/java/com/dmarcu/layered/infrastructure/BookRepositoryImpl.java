package com.dmarcu.layered.infrastructure;

import com.dmarcu.layered.application.exceptions.BookNotFoundException;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.BookRepository;
import com.dmarcu.layered.domain.BookUserCongregate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BookReadDto> getAll() {
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books";
        return jdbcTemplate.query(readQuery, new BeanPropertyRowMapper<>(BookReadDto.class));
    }

    @Override
    public List<BookReadDto> getAllByUserId(int userId) {
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books " +
                "JOIN users_books ON isbn = bookID WHERE userID = ?";
        return jdbcTemplate.query(readQuery, new Object[]{userId},
                new BeanPropertyRowMapper<>(BookReadDto.class));
    }

    @Override
    public BookReadDto getByIsbn(String isbn) {
        String readQuery = "SELECT isbn, authors, title, cover_image, description FROM books " +
                "WHERE isbn = ?";
        List<BookReadDto> books = jdbcTemplate.query(readQuery, new Object[]{isbn},
                new BeanPropertyRowMapper<>(BookReadDto.class));
        if(books.isEmpty()) {
             throw new BookNotFoundException("Book not found");
        }
        return books.get(0);
    }

    @Override
    public void deleteByUsedId(BookUserCongregate bookUserCongregate) {
        String deleteStatement = "DELETE FROM users_books WHERE bookID = ? AND userID = ?";
        jdbcTemplate.update(deleteStatement, bookUserCongregate.getIsbn(), bookUserCongregate.getUserId());
    }

    @Override
    public void addBookToUser(BookUserCongregate bookUserCongregate) {
        String insertStatement = "INSERT INTO users_books (bookID, userID) VALUES (?, ?)";
        jdbcTemplate.update(insertStatement, bookUserCongregate.getIsbn(), bookUserCongregate.getUserId());
    }

    @Override
    public void add(Book book) {
        String insertStatement = "INSERT INTO books " +
                "(isbn, title, authors, year_published, edition_number, cover_image, description) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertStatement, book.getIsbn(), book.getTitle(), book.getAuthors(), book.getYearPublished(),
                book.getEditionNumber(), book.getCoverImagePath(), book.getDescription());
    }
}
