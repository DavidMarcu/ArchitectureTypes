package com.dmarcu.layered.presentaion;

import com.dmarcu.layered.application.Bus;
import com.dmarcu.layered.application.commands.review.*;
import com.dmarcu.layered.application.queries.review.ReviewsQuery;
import com.dmarcu.layered.application.queries.review.ReviewsResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/books/reviews")
public class ReviewController {

    private final Bus bus;

    public ReviewController(Bus bus) {
        this.bus = bus;
    }

    @PostMapping(value = "/review")
    public ResponseEntity<AddReviewResult> addReview(Authentication authentication,
                                     @Valid @RequestBody AddReviewCommand addReviewCommand) {
        addReviewCommand.setUsername(authentication.getName());
        return new ResponseEntity<>(bus.executeCommand(addReviewCommand), HttpStatus.CREATED);
    }

    @PutMapping(value = "/review")
    public ResponseEntity<UpdateReviewResult> updateReview(Authentication authentication,
                               @Valid @RequestBody UpdateReviewCommand updateReviewCommand) {
        updateReviewCommand.setUsername(authentication.getName());
        return new ResponseEntity<>(bus.executeCommand(updateReviewCommand), HttpStatus.OK);
    }

    @GetMapping(value = "/{isbn}", params = {"page"})
    public ResponseEntity<ReviewsResult> getReviews(Authentication authentication,
                                        @PathVariable String isbn, @RequestParam int page) {
        return new ResponseEntity<>(bus.executeQuery(new ReviewsQuery(isbn, page,
                authentication.getName())), HttpStatus.OK);
    }

    @DeleteMapping(value = "/review/{isbn}")
    public ResponseEntity<Void> deleteReview(Authentication authentication,
                                             @PathVariable String isbn) {
        var deleteCommand = new DeleteReviewCommand(authentication.getName(), isbn);
        return new ResponseEntity<>(bus.executeCommand(deleteCommand), HttpStatus.OK);
    }
}
