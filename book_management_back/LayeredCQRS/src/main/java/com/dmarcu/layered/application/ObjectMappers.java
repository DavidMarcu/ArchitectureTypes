package com.dmarcu.layered.application;

import com.dmarcu.layered.application.commands.CreateBookCommand;
import com.dmarcu.layered.application.queries.BooksResult;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookReadDto;

import java.util.Arrays;

public class ObjectMappers {

    public static BooksResult convert(BookReadDto bookReadDto) {
        var bookConverted = new BooksResult();
        bookConverted.setIsbn(bookReadDto.getIsbn());
        bookConverted.setTitle(bookReadDto.getTitle());
        bookConverted.setAuthors(Arrays.asList(bookReadDto.getAuthors().split(", ")));
        bookConverted.setCoverImagePath(bookReadDto.getCoverImage());
        bookConverted.setDescription(bookReadDto.getDescription());
        return bookConverted;
    }

    public static Book convert(CreateBookCommand bookCommand) {
        var book = new Book();
        book.setIsbn(bookCommand.getIsbn());
        book.setTitle(bookCommand.getTitle());
        book.setEditionNumber(bookCommand.getEditionNumber());
        book.setYearPublished(bookCommand.getYearPublished());
        book.setDescription(bookCommand.getDescription());
        String authors = String.join(", ", bookCommand.getAuthors());
        book.setAuthors(authors);
        return book;
    }
}
