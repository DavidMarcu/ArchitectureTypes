package com.dmarcu.layered.application.queries.review;

import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.*;
import com.dmarcu.layered.domain.repositories.ReviewRepository;
import com.dmarcu.layered.domain.repositories.UserRepository;
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
        Page pagination = new Page(query.getPage(), reviewPageSize);
        User user = userRepository.getByUsername(query.getUsername());
        BookUserCongregate congregate = new BookUserCongregate(user.getId(), query.getBookId());
        return getReviews(query, pagination, congregate);
    }

    private ReviewsResult getReviews(ReviewsQuery query, Page pagination,
                                     BookUserCongregate congregate) {
        ReviewsResult.ReviewsResultBuilder resultBuilder;
        if(pagination.getPageNo() == 1) {
            resultBuilder = getFirstPageReviews(pagination, congregate, query.getBookId());
        } else {
            resultBuilder = getOtherPagesReviews(pagination, congregate);
        }
        return resultBuilder.build();
    }

    private ReviewsResult.ReviewsResultBuilder getFirstPageReviews(Page pagination,
                           BookUserCongregate congregate, String bookId) {
        List<Integer> ratings = reviewRepository.ratingsForBook(bookId);
        int count = ratings.size();
        if(count > 0) {
            long ratingsSum = ratings.stream()
                    .mapToLong(Integer::longValue).sum();
            List<ReviewUser> reviewUsers = reviewRepository.getForBook(congregate, pagination);
            ReviewUser loggedUserReview = reviewRepository.getOwnForBook(congregate);
            return ReviewsResult.builder().reviewCount(count)
                    .lastPage(getLastPage(count))
                    .ratingSum(ratingsSum)
                    .otherReviews(reviewUsers)
                    .ownReview(loggedUserReview);
        } else {
            return ReviewsResult.builder()
                    .reviewCount(0)
                    .lastPage(1)
                    .otherReviews(Collections.emptyList());
        }
    }

    private ReviewsResult.ReviewsResultBuilder getOtherPagesReviews(Page pagination, BookUserCongregate congregate) {
        List<ReviewUser> reviewUsers;
        reviewUsers = reviewRepository.getForBook(congregate, pagination);
        return ReviewsResult.builder().otherReviews(reviewUsers);
    }

    private int getLastPage(double totalRatings) {
        return (int)Math.ceil(totalRatings / reviewPageSize);
    }
}
