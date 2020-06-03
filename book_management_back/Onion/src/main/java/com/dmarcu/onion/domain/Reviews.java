package com.dmarcu.onion.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Reviews {

    private int reviewCount;
    private int lastPage;
    private float ratingAvg;
    private List<ReviewUser> otherReviews;
    private ReviewUser ownReview;

}
