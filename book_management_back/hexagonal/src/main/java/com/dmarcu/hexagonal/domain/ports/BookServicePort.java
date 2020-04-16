package com.dmarcu.hexagonal.domain.ports;

import com.dmarcu.hexagonal.application.AddBookRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BookServicePort {

    String addBook(AddBookRequest book) throws JsonProcessingException;

    String addBookByIsbn(String isbn);

    String addBookByTitle(String title);

    String addBookByAuthor(String author);
}
