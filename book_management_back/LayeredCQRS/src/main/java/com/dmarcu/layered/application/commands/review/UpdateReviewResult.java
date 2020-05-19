package com.dmarcu.layered.application.commands.review;

import lombok.Data;

@Data
public class UpdateReviewResult {
    private final byte rating;
    private final String review;
}
