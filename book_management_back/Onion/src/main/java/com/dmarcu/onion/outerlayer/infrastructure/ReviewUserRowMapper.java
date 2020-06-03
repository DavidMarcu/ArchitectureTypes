package com.dmarcu.onion.outerlayer.infrastructure;

import com.dmarcu.onion.domain.Review;
import com.dmarcu.onion.domain.ReviewUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewUserRowMapper implements RowMapper<ReviewUser> {

    @Override
    public ReviewUser mapRow(ResultSet resultSet, int i) throws SQLException {
        var review = new Review(resultSet.getInt("reviewID"), resultSet.getByte("rating"),
                resultSet.getString("review"));
        var username = resultSet.getString("username");
        return new ReviewUser(username, review);
    }
}
