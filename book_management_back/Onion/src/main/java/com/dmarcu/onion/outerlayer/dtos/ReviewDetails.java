package com.dmarcu.onion.outerlayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDetails {
    private int reviewID;
    private String username;
    private byte rating;
    private String review;
}
