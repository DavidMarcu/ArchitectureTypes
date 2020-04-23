package com.dmarcu.layered.application.queries;

import lombok.Data;

import java.util.List;

@Data
public class BooksResult {

    private String isbn;
    private String title;
    private List<String> authors;
    private String coverImagePath;
    private String description;
}
