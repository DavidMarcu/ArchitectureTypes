package com.dmarcu.onion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookRead {
    private String isbn;
    private String title;
    private List<String> authors;
    private String coverImage;
    private String coverImageType;
    private String description;
    private byte editionNumber;

    public BookRead(String isbn, String title, List<String> authors, String coverImage,
                    String coverImageType, String description) {
        this(isbn, title, authors, coverImage, coverImageType, description, (byte) 0);
    }
}

