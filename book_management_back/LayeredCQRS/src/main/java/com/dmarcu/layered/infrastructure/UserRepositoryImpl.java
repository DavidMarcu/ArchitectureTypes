package com.dmarcu.layered.infrastructure;

import com.dmarcu.layered.application.exceptions.DuplicateUserException;
import com.dmarcu.layered.application.exceptions.UserNotFoundException;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.UserRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String AUTHORITY = "ROLE_USER";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add(User user) {
        String queryStatement = "SELECT username FROM users WHERE username = ?";
        List<String> users = jdbcTemplate.query(queryStatement, new Object[]{user.getUsername()},
                new BeanPropertyRowMapper<>(String.class));
        if(!users.isEmpty()) {
            throw new DuplicateUserException("User already exists");
        }
        String insertStatement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertStatement, user.getUsername(), user.getPassword(), user.getEmail());
        insertStatement = "INSERT INTO authorities(username, authority) VALUES (?, ?)";
        jdbcTemplate.update(insertStatement, user.getUsername(), AUTHORITY);
    }

    @Override
    public User getByUsername(String username) {
        String userQuery = "SELECT id, username, password FROM users WHERE username = ?";
        User user = jdbcTemplate.queryForObject(userQuery, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        if(user == null) {
            throw new UserNotFoundException("Invalid credentials");
        }
        return user;
    }
}
