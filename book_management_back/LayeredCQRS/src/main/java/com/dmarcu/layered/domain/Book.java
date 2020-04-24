package com.dmarcu.layered.domain;

import lombok.*;

@Getter
@Setter
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
    private String coverImagePath;

    public void uploadCoverImage(byte[] imageBytes) {

    }
}
