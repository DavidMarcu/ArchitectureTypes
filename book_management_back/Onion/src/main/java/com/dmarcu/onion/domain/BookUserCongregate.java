package com.dmarcu.onion.domain;

import lombok.Data;

@Data
public class BookUserCongregate {
    private final int userId;
    private final String isbn;
}
