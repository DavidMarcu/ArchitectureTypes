package com.dmarcu.layered.domain;

import lombok.Data;

@Data
public class Review {

    private int id;
    private byte rating;
    private String review;

    public Review(byte rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
