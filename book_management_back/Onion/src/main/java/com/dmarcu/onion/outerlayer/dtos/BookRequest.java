package com.dmarcu.onion.outerlayer.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class BookRequest {

    @Pattern(regexp = "\\d{10}|\\d{13}", message = "isbn should be of length 10 or 13")
    private String isbn;
    @NotEmpty
    private String title;
    @NotEmpty
    private List<String> authors;
    private String coverImage;
    @Pattern(regexp = "jpg|png", flags = Pattern.Flag.CASE_INSENSITIVE, message = "image type not supported")
    private String coverImageType;
    private String description;
    private short yearPublished;
    private byte editionNumber;
}
