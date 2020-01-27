package com.dmarcu.hexagonal.domain;

import lombok.Data;

@Data
public class Book {

    private String isbn;

    private String title;

    private String authors;

}
