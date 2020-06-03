package com.dmarcu.onion.outerlayer.services;

import com.dmarcu.onion.application.ReviewService;
import com.dmarcu.onion.domain.*;
import com.dmarcu.onion.domain.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    @Value("${reviews.page_size}")
    private int reviewPageSize;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review addReview(ReviewCongregate reviewDetails) {
        reviewRepository.add(reviewDetails.getReview(), reviewDetails.getBookUserCongregate());
        return reviewDetails.getReview();
    }

    @Override
    public Review updateReview(ReviewCongregate reviewDetails) {
        reviewRepository.update(reviewDetails.getReview(), reviewDetails.getBookUserCongregate());
        return reviewDetails.getReview();
    }

    @Override
    public void deleteReview(User user, String isbn) {
        reviewRepository.delete(new BookUserCongregate(user.getId(), isbn));
    }

    @Override
    public Reviews getAllReviews(User user, String isbn, int page) {
        var pagination = new Page(page, reviewPageSize);
        var congregate = new BookUserCongregate(user.getId(), isbn);
        return getReviews(pagination, congregate);
    }

    private Reviews getReviews(Page pagination, BookUserCongregate congregate) {
        Reviews.ReviewsBuilder resultBuilder;
        if(pagination.getPageNo() == 1) {
            resultBuilder = getFirstPageReviews(pagination, congregate);
        } else {
            resultBuilder = getOtherPagesReviews(pagination, congregate);
        }
        return resultBuilder.build();
    }

    private Reviews.ReviewsBuilder getFirstPageReviews(Page pagination, BookUserCongregate congregate) {
        List<Integer> ratings = reviewRepository.ratingsForBook(congregate.getIsbn());
        int count = ratings.size();
        if(count > 0) {
            Double ratingsAverage = ratings.stream()
                    .collect(Collectors.averagingDouble(Double::valueOf));
            List<ReviewUser> reviewUsers = reviewRepository.getForBook(congregate, pagination);
            ReviewUser loggedUserReview = reviewRepository.getOwnForBook(congregate);
            return Reviews.builder().reviewCount(count)
                    .lastPage(getLastPage(count))
                    .ratingAvg(ratingsAverage.floatValue())
                    .otherReviews(reviewUsers)
                    .ownReview(loggedUserReview);
        } else {
            return Reviews.builder()
                    .reviewCount(0)
                    .lastPage(1)
                    .otherReviews(Collections.emptyList());
        }
    }

    private Reviews.ReviewsBuilder getOtherPagesReviews(Page pagination, BookUserCongregate congregate) {
        List<ReviewUser> reviewUsers;
        reviewUsers = reviewRepository.getForBook(congregate, pagination);
        return Reviews.builder().otherReviews(reviewUsers);
    }

    private int getLastPage(double totalRatings) {
        return (int)Math.ceil(totalRatings / reviewPageSize);
    }
}
