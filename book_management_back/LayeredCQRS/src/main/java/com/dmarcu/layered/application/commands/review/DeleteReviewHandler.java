package com.dmarcu.layered.application.commands.review;

import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.BookUserCongregate;
import com.dmarcu.layered.domain.ReviewRepository;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteReviewHandler implements CommandHandler<Void, DeleteReviewCommand> {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public DeleteReviewHandler(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Void handle(DeleteReviewCommand command) {
        User user = userRepository.getByUsername(command.getUsername());
        reviewRepository.delete(new BookUserCongregate(user.getId(), command.getIsbn()));
        return null;
    }
}
