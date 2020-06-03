package com.dmarcu.onion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewUser {

    private String username;
    private Review review;

}

