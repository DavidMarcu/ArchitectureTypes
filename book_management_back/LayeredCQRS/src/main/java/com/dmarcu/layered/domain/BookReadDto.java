package com.dmarcu.layered.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookReadDto {
    private String isbn;
    private String title;
    private String authors;
    private String coverImage;
    private String description;
}
