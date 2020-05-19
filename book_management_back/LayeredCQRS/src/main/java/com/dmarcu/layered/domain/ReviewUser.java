package com.dmarcu.layered.domain;

import lombok.Data;

@Data
public class ReviewUser {

    private int reviewID;
    private String username;
    private byte rating;
    private String review;

}
