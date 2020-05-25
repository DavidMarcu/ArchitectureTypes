package com.dmarcu.layered.application.queries.review;

import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewsHandler implements QueryHandler<ReviewsResult, ReviewsQuery> {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Value("${reviews.page_size}")
    private int reviewPageSize;

    public ReviewsHandler(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewsResult handle(ReviewsQuery query) {
        ReviewsResult.ReviewsResultBuilder resultBuilder = ReviewsResult.builder();
        Page pagination = new Page(query.getPage(), reviewPageSize);
        User user = userRepository.getByUsername(query.getUsername());
        BookUserCongregate congregate = new BookUserCongregate(user.getId(), query.getBookId());
        List<ReviewUser> reviewUsers;
        if(pagination.getPageNo() == 1) {
            List<Integer> ratings = reviewRepository.ratingsForBook(query.getBookId());
            int count = ratings.size();
            if(count > 0) {
                Double ratingsAverage = ratings.stream()
                        .collect(Collectors.averagingDouble(Double::valueOf));
                reviewUsers = reviewRepository.getForBook(congregate, pagination);
                ReviewUser loggedUserReview = reviewRepository.getOwnForBook(congregate);
                resultBuilder.reviewCount(count)
                        .lastPage(getLastPage(count))
                        .ratingAvg(ratingsAverage.floatValue())
                        .otherReviews(reviewUsers)
                        .ownReview(loggedUserReview);
            } else {
                resultBuilder.reviewCount(0).lastPage(1).otherReviews(Collections.emptyList());
            }
        } else {
            reviewUsers = reviewRepository.getForBook(congregate,pagination);
            resultBuilder.otherReviews(reviewUsers);
        }
        return resultBuilder.build();
    }

    private int getLastPage(double totalRatings) {
        return (int)Math.ceil(totalRatings / reviewPageSize);
    }
}
