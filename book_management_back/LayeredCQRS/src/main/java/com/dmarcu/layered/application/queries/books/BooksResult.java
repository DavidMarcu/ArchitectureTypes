package com.dmarcu.layered.application.queries.books;

import lombok.Data;

import java.util.List;

@Data
public class BooksResult {

    private String isbn;
    private String title;
    private List<String> authors;
    private String coverImage;
    private String coverImageType;
}
