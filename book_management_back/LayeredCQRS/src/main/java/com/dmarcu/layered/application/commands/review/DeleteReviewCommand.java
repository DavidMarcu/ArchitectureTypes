package com.dmarcu.layered.application.commands.review;

import com.dmarcu.layered.application.commands.Command;
import lombok.Data;

@Data
public class DeleteReviewCommand implements Command<Void> {
    private final String username;
    private final String isbn;
}
