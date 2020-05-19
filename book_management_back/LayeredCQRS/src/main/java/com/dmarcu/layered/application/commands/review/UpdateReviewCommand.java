package com.dmarcu.layered.application.commands.review;

import com.dmarcu.layered.application.commands.Command;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class UpdateReviewCommand implements Command<UpdateReviewResult> {
    private String username;
    private String isbn;

    @Min(value = 1, message = "Value must be at least 1")
    @Max(value = 5, message = "Value must be at most 5")
    private byte rating;
    private String review;
}
