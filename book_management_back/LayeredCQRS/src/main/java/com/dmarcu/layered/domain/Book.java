package com.dmarcu.layered.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @NonNull
    private String isbn;
    @NonNull
    private String title;
    @NonNull
    private String authors;
    private String description;
    private short yearPublished;
    private byte editionNumber;
    private String coverImage;

}
