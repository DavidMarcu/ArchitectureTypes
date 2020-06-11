package com.dmarcu.onion.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Reviews {

    private int reviewCount;
    private int lastPage;
    private long ratingSum;
    private List<ReviewUser> otherReviews;
    private ReviewUser ownReview;

}
