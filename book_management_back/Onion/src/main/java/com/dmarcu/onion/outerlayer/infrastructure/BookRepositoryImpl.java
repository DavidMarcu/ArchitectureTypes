package com.dmarcu.onion.outerlayer.infrastructure;

import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.domain.BookUserCongregate;
import com.dmarcu.onion.domain.Page;
import com.dmarcu.onion.domain.repositories.BookRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getAll(Page pagination) {
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{pagination.getPageSize(),
                getOffset(pagination)}, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public List<Book> getAllBySearchTerm(Page pagination, String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books " +
                "WHERE title LIKE ? OR authors LIKE ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{searchTerm, searchTerm,
                        pagination.getPageSize(), getOffset(pagination)},
                new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public List<Book> getAllByUserId(int userId, Page pagination) {
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books " +
                "JOIN users_books ON isbn = bookID WHERE userID = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{userId, pagination.getPageSize(),
                getOffset(pagination)}, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public List<Book> getAllByUserIdAndSearchTerm(int userId, Page pagination, String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        String readQuery = "SELECT isbn, authors, title, cover_image FROM books JOIN users_books " +
                "ON isbn = bookID WHERE userID = ? AND (title LIKE ? OR authors LIKE ?) " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(readQuery, new Object[]{userId, searchTerm, searchTerm,
                        pagination.getPageSize(), getOffset(pagination)},
                new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public int getCount() {
        String countQuery = "SELECT count(1) FROM books";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        return count != null ? count : 0;
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
    public int getCountOfUser(int userId) {
        String countQuery = "SELECT count(1) FROM users_books WHERE userID = ?";
        Integer count = jdbcTemplate.queryForObject(countQuery, new Object[]{userId}, Integer.class);
        return count != null ? count : 0;
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
    public Book getByIsbn(String isbn) {
        String readQuery = "SELECT isbn, authors, title, cover_image, description FROM books " +
                "WHERE isbn = ?";
        try {
            return jdbcTemplate.queryForObject(readQuery, new Object[]{isbn},
                    new BeanPropertyRowMapper<>(Book.class));
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public int getCountOfUserByIsbn(BookUserCongregate congregate) {
        String countQuery = "SELECT count(1) FROM users_books WHERE userID = ? AND bookID = ?";
        Integer count = jdbcTemplate.queryForObject(countQuery, new Object[]{congregate.getUserId()
                , congregate.getIsbn()}, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public String add(Book book) {
        String insertStatement = "INSERT INTO books " +
                "(isbn, title, authors, year_published, edition_number, cover_image, description) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insertStatement, book.getIsbn(), book.getTitle(), book.getAuthors(), book.getYearPublished(),
                    book.getEditionNumber(), book.getCoverImage(), book.getDescription());
            return book.getIsbn();
        } catch (DuplicateKeyException duplicateException) {
            return null;
        }
    }

    @Override
    public void addBookToUser(BookUserCongregate bookUserCongregate) {
        String insertStatement = "INSERT INTO users_books (bookID, userID) VALUES (?, ?)";
        jdbcTemplate.update(insertStatement, bookUserCongregate.getIsbn(), bookUserCongregate.getUserId());
    }

    @Override
    public void deleteByUsedId(BookUserCongregate bookUserCongregate) {
        String deleteStatement = "DELETE FROM users_books WHERE bookID = ? AND userID = ?";
        jdbcTemplate.update(deleteStatement, bookUserCongregate.getIsbn(), bookUserCongregate.getUserId());
    }

    private int getOffset(Page pagination) {
        return (pagination.getPageNo() - 1) * pagination.getPageSize();
    }
}
