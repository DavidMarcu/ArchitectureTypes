package com.dmarcu.layered.application.commands.review;

import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.*;
import com.dmarcu.layered.domain.repositories.ReviewRepository;
import com.dmarcu.layered.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateReviewHandler implements CommandHandler<UpdateReviewResult, UpdateReviewCommand> {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public UpdateReviewHandler(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UpdateReviewResult handle(UpdateReviewCommand command) {
        User authenticatedUser = userRepository.getByUsername(command.getUsername());
        reviewRepository.update(new Review(command.getRating(), command.getReview()),
                new BookUserCongregate(authenticatedUser.getId(), command.getIsbn()));
        return new UpdateReviewResult(command.getRating(), command.getReview());
    }
}
