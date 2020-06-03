package com.dmarcu.onion.outerlayer.dtos;

import lombok.Data;

@Data
public class ReviewResponse {
    private final byte rating;
    private final String review;
}
