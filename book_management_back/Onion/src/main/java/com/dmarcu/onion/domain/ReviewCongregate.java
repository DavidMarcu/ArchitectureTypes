package com.dmarcu.onion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewCongregate {

    private BookUserCongregate bookUserCongregate;
    private Review review;
}
