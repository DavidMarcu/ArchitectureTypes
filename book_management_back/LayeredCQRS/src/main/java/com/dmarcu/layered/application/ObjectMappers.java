package com.dmarcu.layered.application;

import com.dmarcu.layered.application.read.BookDisplay;
import com.dmarcu.layered.domain.BookReadDto;

import java.util.Arrays;

public class ObjectMappers {

    public static BookDisplay convert(BookReadDto bookReadDto) {
        var bookConverted = new BookDisplay();
        bookConverted.setIsbn(bookReadDto.getIsbn());
        bookConverted.setTitle(bookReadDto.getTitle());
        bookConverted.setAuthors(Arrays.asList(bookReadDto.getAuthors().split(", ")));
        bookConverted.setCoverImagePath(bookReadDto.getCoverImage());
        String shortDescription = bookReadDto.getDescription().length() > 100 ?
                bookReadDto.getDescription().substring(0, 100) + "..." :
                bookReadDto.getDescription();
        bookConverted.setShortDescription(shortDescription);
        return bookConverted;
    }
}
