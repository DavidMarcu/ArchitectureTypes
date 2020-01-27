package com.dmarcu.hexagonal.domain.adapters;

import com.dmarcu.hexagonal.application.AddBookRequest;
import com.dmarcu.hexagonal.domain.Book;
import com.dmarcu.hexagonal.domain.ports.BookRepositoryPort;
import com.dmarcu.hexagonal.domain.ports.BookServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class BookServiceAdapter implements BookServicePort {

    private BookRepositoryPort bookRepository;

    public BookServiceAdapter(BookRepositoryPort bookRepositoryPort){
        bookRepository = bookRepositoryPort;
    }

    @Override
    public String addBook(AddBookRequest bookRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = objectMapper.readValue(objectMapper.writeValueAsString(bookRequest), Book.class);
        return bookRepository.addBook(book);
    }
}
