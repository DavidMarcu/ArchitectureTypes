package com.dmarcu.hexagonal.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
public class AddBookRequest {

    @JsonProperty("isbn")
    @Pattern(regexp = "^\\d{10}|\\d{13}$")
    private final String isbn;

    @JsonProperty("title")
    @Size(min = 1, max = 300)
    private final String bookTitle;

    @JsonProperty("authors")
    @Size(min = 1, max = 2000)
    private final String bookAuthors;

}
