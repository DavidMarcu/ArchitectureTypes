package com.dmarcu.layered.application.queries.review;

import com.dmarcu.layered.domain.ReviewUser;
import lombok.Data;

import java.util.List;

@Data
public class ReviewsResult {

  private final List<ReviewUser> otherReviews;
  private final ReviewUser ownReview;

}
