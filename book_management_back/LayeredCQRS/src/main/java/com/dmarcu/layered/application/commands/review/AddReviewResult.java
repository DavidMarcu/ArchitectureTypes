package com.dmarcu.layered.application.commands.review;

import lombok.Data;

@Data
public class AddReviewResult {
    private final byte rating;
    private final String review;
}
