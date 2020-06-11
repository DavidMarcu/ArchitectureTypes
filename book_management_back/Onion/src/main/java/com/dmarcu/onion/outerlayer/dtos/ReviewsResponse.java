package com.dmarcu.onion.outerlayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewsResponse {
    private int reviewCount;
    private int lastPage;
    private long ratingSum;
    private List<ReviewDetails> otherReviews;
    private ReviewDetails ownReview;
}
