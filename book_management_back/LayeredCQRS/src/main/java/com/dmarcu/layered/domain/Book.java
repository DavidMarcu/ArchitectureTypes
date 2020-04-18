package com.dmarcu.layered.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Book {

    @NonNull
    private String isbn;
    @NonNull
    private String title;
    @NonNull
    private String authors;
    private short yearPublished;
    private byte editionNumber;
    private String coverImagePath;
    private byte rating;
    private String review;
}
