package com.dmarcu.layered.infrastructure;

import com.dmarcu.layered.application.UserNotFoundException;
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
        String insertStatement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertStatement, user.getUsername(), user.getPassword(), user.getEmail());
        insertStatement = "INSERT INTO authorities(username, authority) VALUES (?, ?)";
        jdbcTemplate.update(insertStatement, user.getUsername(), AUTHORITY);
    }

    @Override
    public User getByUsername(String username) {
        String userQuery = "SELECT id, username, password, enabled FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(userQuery, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        if(users.isEmpty()) {
            throw new UserNotFoundException();
        }
        return users.get(0);
    }
}
