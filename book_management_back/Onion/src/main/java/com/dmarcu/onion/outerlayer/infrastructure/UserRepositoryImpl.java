package com.dmarcu.onion.outerlayer.infrastructure;

import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.domain.repositories.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String AUTHORITY = "ROLE_USER";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add(User user) {
        String insertStatement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertStatement, user.getUsername(), user.getPassword(), user.getEmail());
        insertStatement = "INSERT INTO authorities(username, authority) VALUES (?, ?)";
        jdbcTemplate.update(insertStatement, user.getUsername(), AUTHORITY);
    }

    @Override
    public int count(User user) {
        String queryStatement = "SELECT count(1) FROM users WHERE username = ?";
        Integer userCount = jdbcTemplate.queryForObject(queryStatement, new Object[]{user.getUsername()},
                Integer.class);
        return userCount != null ? userCount : 0;
    }

    @Override
    public User getByUsername(String username) {
        String userQuery = "SELECT id, username, password FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(userQuery, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
