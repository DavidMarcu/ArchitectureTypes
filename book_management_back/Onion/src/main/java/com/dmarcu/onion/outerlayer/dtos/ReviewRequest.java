package com.dmarcu.onion.outerlayer.dtos;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ReviewRequest {
    private String isbn;

    @Min(value = 1, message = "Rating should be at least 1")
    @Max(value = 5, message = "Rating should be at most 5")
    private byte rating;
    private String review;
}
