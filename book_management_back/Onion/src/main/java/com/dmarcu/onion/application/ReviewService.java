package com.dmarcu.onion.application;

import com.dmarcu.onion.domain.Review;
import com.dmarcu.onion.domain.ReviewCongregate;
import com.dmarcu.onion.domain.Reviews;
import com.dmarcu.onion.domain.User;

public interface ReviewService {
    Review addReview(ReviewCongregate review);
    Reviews getAllReviews(User user, String isbn, int page);
    Review updateReview(ReviewCongregate review);
    void deleteReview(User user, String isbn);
}
