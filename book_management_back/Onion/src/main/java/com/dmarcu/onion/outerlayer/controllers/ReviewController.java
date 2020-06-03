package com.dmarcu.onion.outerlayer.controllers;

import com.dmarcu.onion.application.ReviewService;
import com.dmarcu.onion.application.UserService;
import com.dmarcu.onion.domain.Review;
import com.dmarcu.onion.domain.Reviews;
import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.outerlayer.dtos.ReviewRequest;
import com.dmarcu.onion.outerlayer.dtos.ReviewResponse;
import com.dmarcu.onion.outerlayer.dtos.ReviewsResponse;
import com.dmarcu.onion.outerlayer.mappers.ReviewMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/books/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, UserService userService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping(value = "/{isbn}", params = {"page"})
    public ResponseEntity<ReviewsResponse> getReviews(Authentication authentication,
                                                      @PathVariable String isbn, @RequestParam int page) {
        User user = userService.findUserByUsername(authentication.getName());
        Reviews reviews = reviewService.getAllReviews(user, isbn, page);
        return new ResponseEntity<>(reviewMapper.domainToResponse(reviews), HttpStatus.OK);
    }

    @PostMapping(value = "/review")
    public ResponseEntity<ReviewResponse> addReview(Authentication authentication,
                                                    @Valid @RequestBody ReviewRequest review) {
        User user = userService.findUserByUsername(authentication.getName());
        Review reviewAdded = reviewService.addReview(reviewMapper.requestToDomain(user, review));
        return new ResponseEntity<>(reviewMapper.domainToResponse(reviewAdded), HttpStatus.CREATED);
    }

    @PutMapping(value = "/review")
    public ResponseEntity<ReviewResponse> updateReview(Authentication authentication,
                                                       @Valid @RequestBody ReviewRequest review) {
        User user = userService.findUserByUsername(authentication.getName());
        Review reviewUpdated = reviewService.updateReview(reviewMapper.requestToDomain(user, review));
        return new ResponseEntity<>(reviewMapper.domainToResponse(reviewUpdated), HttpStatus.OK);
    }

    @DeleteMapping(value = "/review/{isbn}")
    public ResponseEntity<Void> deleteReview(Authentication authentication,
                                             @PathVariable String isbn) {
        User user = userService.findUserByUsername(authentication.getName());
        reviewService.deleteReview(user, isbn);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
