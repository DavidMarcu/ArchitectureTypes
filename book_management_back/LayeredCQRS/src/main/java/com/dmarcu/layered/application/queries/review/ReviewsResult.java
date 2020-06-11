package com.dmarcu.layered.application.queries.review;

import com.dmarcu.layered.domain.ReviewUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewsResult {

  private int reviewCount;
  private int lastPage;
  private float ratingSum;
  private List<ReviewUser> otherReviews;
  private ReviewUser ownReview;

}
