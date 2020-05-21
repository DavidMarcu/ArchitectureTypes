package com.dmarcu.layered.infrastructure;

import com.dmarcu.layered.application.exceptions.BookNotFoundException;
import com.dmarcu.layered.domain.Book;
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
    public int getCount() {
        String countQuery = "SELECT count(1) FROM books";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public List<Book> getAll(int page, int pageSize) {
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{pageSize, (page - 1) * pageSize}, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public int getCountOfUser(int userId) {
        String countQuery = "SELECT count(1) FROM users_books WHERE userID = ?";
        Integer count = jdbcTemplate.queryForObject(countQuery, new Object[]{userId}, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public List<Book> getAllByUserId(int userId, int page, int pageSize) {
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books " +
                "JOIN users_books ON isbn = bookID WHERE userID = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{userId, pageSize, (page - 1) * pageSize},
                new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public int getCountBySearchTerm(String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        String countQuery = "SELECT count(1) FROM books WHERE title LIKE ? OR authors LIKE ?";
        Integer count = jdbcTemplate.queryForObject(countQuery, new Object[]{searchTerm, searchTerm},
                Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public List<Book> getAllBySearchTerm(int page, int pageSize, String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books " +
                "WHERE title LIKE ? OR authors LIKE ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{searchTerm, searchTerm, pageSize,
                (page - 1) * pageSize}, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public int getCountOfUserBySearchTerm(int userId, String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        String countQuery = "SELECT count(1) FROM books JOIN users_books ON isbn = bookID " +
                "WHERE userID = ? AND (title LIKE ? OR authors LIKE ?)";
        Integer count = jdbcTemplate.queryForObject(countQuery,
                new Object[]{userId, searchTerm, searchTerm}, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public List<Book> getAllByUserIdAndSearchTerm(int userId, int page, int pageSize, String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books JOIN users_books " +
                "ON isbn = bookID WHERE userID = ? AND (title LIKE ? OR authors LIKE ?) " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{userId, searchTerm, searchTerm, pageSize,
                (page - 1) * pageSize}, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book getByIsbn(String isbn) {
        String readQuery = "SELECT isbn, authors, title, cover_image, description FROM books " +
                "WHERE isbn = ?";
        List<Book> books = jdbcTemplate.query(readQuery, new Object[]{isbn},
                new BeanPropertyRowMapper<>(Book.class));
        if(books.isEmpty()) {
             throw new BookNotFoundException("Book not found");
        }
        return books.get(0);
    }

    @Override
    public int getCountOfUserByIsbn(int userId, String isbn) {
        String countQuery = "SELECT count(1) FROM users_books WHERE userID = ? AND bookID = ?";
        Integer count = jdbcTemplate.queryForObject(countQuery, new Object[]{userId, isbn}, Integer.class);
        return count != null ? count : 0;
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
                book.getEditionNumber(), book.getCoverImage(), book.getDescription());
    }
}
