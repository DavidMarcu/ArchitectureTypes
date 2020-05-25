package com.dmarcu.layered.infrastructure;

import com.dmarcu.layered.domain.*;
import com.dmarcu.layered.domain.repositories.ReviewRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReviewRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add(Review review, BookUserCongregate congregate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String insertReviewStatement = "INSERT INTO reviews(rating, review) VALUES(?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertReviewStatement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getReview());
            return ps;
        }, keyHolder);
        String insertCongregateStatement = "INSERT INTO reviews_users_books(reviewID, userID, bookID) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(insertCongregateStatement, keyHolder.getKey(),
                congregate.getUserId(), congregate.getIsbn());
    }

    @Override
    public void update(Review review, BookUserCongregate congregate) {
        String updateStatement = "UPDATE reviews SET rating = ?, review = ?" +
                " WHERE id = (SELECT reviewID FROM reviews_users_books WHERE userID = ? AND bookID = ?)";
        jdbcTemplate.update(updateStatement, review.getRating(), review.getReview(),
                congregate.getUserId(), congregate.getIsbn());
    }

    @Override
    public List<Integer> ratingsForBook(String isbn) {
        String ratingQuery = "SELECT rating FROM reviews_users_books JOIN reviews ON reviewID = id" +
                " WHERE bookID = ? ";
        return jdbcTemplate.queryForList(ratingQuery, new Object[]{isbn}, Integer.class);
    }

    @Override
    public List<ReviewUser> getForBook(BookUserCongregate congregate, Page pagination) {
        String queryForReviews = "SELECT reviewID, username, review, rating FROM reviews_users_books " +
                "JOIN users ON users.id = userID JOIN reviews ON reviews.id = reviewID " +
                "WHERE bookID = ? AND userID != ?  LIMIT ? OFFSET ?";
        int offset = (pagination.getPageNo() - 1) * pagination.getPageSize();
        return jdbcTemplate.query(queryForReviews,
                new Object[]{congregate.getIsbn(), congregate.getUserId(),
                        pagination.getPageSize(), offset},
                new BeanPropertyRowMapper<>(ReviewUser.class));
    }

    @Override
    public ReviewUser getOwnForBook(BookUserCongregate congregate) {
        String ownReviewQuery = "SELECT review, rating FROM reviews JOIN reviews_users_books " +
                "ON reviews.id = reviewID JOIN users ON users.id = userID WHERE userID = ? AND " +
                "bookID = ?";
        try {
         return jdbcTemplate.queryForObject(ownReviewQuery, new Object[]{congregate.getUserId(),
                    congregate.getIsbn()}, new BeanPropertyRowMapper<>(ReviewUser.class));
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }


    @Override
    public void delete(BookUserCongregate congregate) {
        String deleteStatement = "DELETE FROM reviews WHERE id = (SELECT reviewID FROM " +
                "reviews_users_books WHERE userID = ? AND bookID = ?)";
        jdbcTemplate.update(deleteStatement, congregate.getUserId(), congregate.getIsbn());
    }
}
