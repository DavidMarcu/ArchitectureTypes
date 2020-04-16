package com.dmarcu.hexagonal.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddBookResponse {

    @JsonProperty("isbn")
    private final String isbn;
}
