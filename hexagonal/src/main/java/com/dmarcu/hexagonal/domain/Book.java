package com.dmarcu.hexagonal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Book {

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("author")
    private String author;

    @JsonProperty("title")
    private String title;

}
