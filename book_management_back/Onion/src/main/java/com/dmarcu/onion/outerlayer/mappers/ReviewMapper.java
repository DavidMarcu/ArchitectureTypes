package com.dmarcu.onion.outerlayer.mappers;

import com.dmarcu.onion.domain.*;
import com.dmarcu.onion.outerlayer.dtos.ReviewDetails;
import com.dmarcu.onion.outerlayer.dtos.ReviewRequest;
import com.dmarcu.onion.outerlayer.dtos.ReviewResponse;
import com.dmarcu.onion.outerlayer.dtos.ReviewsResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReviewMapper {

    public ReviewCongregate requestToDomain(User user, ReviewRequest reviewRequest) {
        var bookUserCongregate = new BookUserCongregate(user.getId(), reviewRequest.getIsbn());
        var review = new Review(reviewRequest.getRating(), reviewRequest.getReview());
        return new ReviewCongregate(bookUserCongregate, review);
    }

    public ReviewResponse domainToResponse(Review review) {
        return new ReviewResponse(review.getRating(), review.getReview());
    }

    public ReviewsResponse domainToResponse(Reviews reviews) {
        var ownReview = reviews.getOwnReview() != null ? convert(reviews.getOwnReview()) : null;
        return new ReviewsResponse(reviews.getReviewCount(), reviews.getLastPage(), reviews.getRatingAvg(),
                reviews.getOtherReviews().stream().map(this::convert).collect(Collectors.toList()),
                ownReview);
    }

    private ReviewDetails convert(ReviewUser review) {
        return new ReviewDetails(review.getReview().getId(), review.getUsername(), review.getReview().getRating(),
                review.getReview().getReview());
    }
}
