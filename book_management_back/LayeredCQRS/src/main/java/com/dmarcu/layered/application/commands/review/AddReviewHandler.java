package com.dmarcu.layered.application.commands.review;

import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.*;
import com.dmarcu.layered.domain.repositories.ReviewRepository;
import com.dmarcu.layered.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddReviewHandler implements CommandHandler<AddReviewResult, AddReviewCommand> {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public AddReviewHandler(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddReviewResult handle(AddReviewCommand command) {
        var review = new Review(command.getRating(), command.getReview());
        User user = userRepository.getByUsername(command.getUsername());
        var bookUserCongragate = new BookUserCongregate(user.getId(), command.getIsbn());
        reviewRepository.add(review, bookUserCongragate);
        return new AddReviewResult(review.getRating(), review.getReview());
    }
}
