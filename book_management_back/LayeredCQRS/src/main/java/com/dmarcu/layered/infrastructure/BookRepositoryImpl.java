package com.dmarcu.layered.infrastructure;

import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.BookRepository;
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
        String readQuery = "SELECT isbn, authors, title, cover_image, description FROM books";
        return jdbcTemplate.query(readQuery, new BeanPropertyRowMapper<>(BookReadDto.class));
    }
}
