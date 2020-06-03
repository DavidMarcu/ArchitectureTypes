package com.dmarcu.onion.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {

    private String isbn;
    private String authors;
    private String title;
    private String coverImage;
    private short yearPublished;
    private byte editionNumber;
    private String description;

    public Book(String isbn, String authors, String title, String coverImage, byte editionNumber, String description) {
        this.isbn = isbn;
        this.authors = authors;
        this.title = title;
        this.coverImage = coverImage;
        this.editionNumber = editionNumber;
        this.description = description;
    }
}
