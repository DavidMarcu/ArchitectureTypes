package com.dmarcu.onion.domain;

import lombok.Data;

@Data
public class Book {

    private String isbn;
    private String authors;
    private String title;
    private String coverImage;
    private String description;
}
