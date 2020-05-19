package com.dmarcu.layered.application.queries.review;

import com.dmarcu.layered.application.queries.Query;
import lombok.Data;

@Data
public class ReviewsQuery implements Query<ReviewsResult> {
    private final String bookId;
    private final String username;

}
