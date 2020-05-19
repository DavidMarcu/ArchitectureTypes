package com.dmarcu.layered.application.queries.review;

import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.ReviewRepository;
import com.dmarcu.layered.domain.ReviewUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewsHandler implements QueryHandler<ReviewsResult, ReviewsQuery> {

    private final ReviewRepository reviewRepository;

    public ReviewsHandler(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewsResult handle(ReviewsQuery query) {
        List<ReviewUser> reviewUsers = reviewRepository.getForBook(query.getBookId());
        List<ReviewUser> otherReviews = new ArrayList<>();
        ReviewUser ownReview = null;
        for(ReviewUser review: reviewUsers) {
            if (query.getUsername().equals(review.getUsername())) {
                ownReview = review;
            } else {
                otherReviews.add(review);
            }
        }
        return new ReviewsResult(otherReviews, ownReview);
    }
}
