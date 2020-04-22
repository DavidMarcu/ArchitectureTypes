package com.dmarcu.layered.application.read;

import lombok.Data;

import java.util.List;

@Data
public class BookDisplay {

    private String isbn;
    private String title;
    private List<String> authors;
    private String coverImagePath;
    private String description;
}
