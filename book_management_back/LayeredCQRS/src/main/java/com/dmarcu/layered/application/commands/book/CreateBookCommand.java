package com.dmarcu.layered.application.commands.book;

import com.dmarcu.layered.application.commands.Command;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class CreateBookCommand implements Command<CreateBookResult> {

    @Pattern(regexp = "\\d{10}|\\d{13}", message = "isbn should be of length 10 or 13")
    private String isbn;
    @NotEmpty
    private String title;
    @NotEmpty
    private List<String> authors;
    private byte[] coverImage;
    @Pattern(regexp = "jpg|png", flags = Pattern.Flag.CASE_INSENSITIVE, message = "image type not supported")
    private String coverImageType;
    private String description;
    private short yearPublished;
    private byte editionNumber;
}
