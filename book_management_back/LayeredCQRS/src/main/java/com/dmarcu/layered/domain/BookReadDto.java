package com.dmarcu.layered.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookReadDto {
    private String isbn;
    private String title;
    private List<String> authors;
    private String coverImage;
    private String coverImageType;
    private String description;
}
