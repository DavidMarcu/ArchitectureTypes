package com.dmarcu.onion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

