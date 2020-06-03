package com.dmarcu.onion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Review {

    private int id;
    private byte rating;
    private String review;

    public Review(byte rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
