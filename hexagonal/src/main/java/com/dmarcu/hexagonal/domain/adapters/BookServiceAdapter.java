package com.dmarcu.hexagonal.domain.adapters;

import com.dmarcu.hexagonal.application.AddBookRequest;
import com.dmarcu.hexagonal.domain.Book;
import com.dmarcu.hexagonal.domain.ports.BookRepositoryPort;
import com.dmarcu.hexagonal.domain.ports.BookServicePort;
import com.dmarcu.hexagonal.infrastructure.GoogleApiWebClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class BookServiceAdapter implements BookServicePort {

    private BookRepositoryPort bookRepository;
    private GoogleApiWebClient googleApiWebClient;

    public BookServiceAdapter(BookRepositoryPort bookRepositoryPort, GoogleApiWebClient googleApiWebClient){
        bookRepository = bookRepositoryPort;
        this.googleApiWebClient = googleApiWebClient;
    }

    @Override
    public String addBook(AddBookRequest bookRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = objectMapper.readValue(objectMapper.writeValueAsString(bookRequest), Book.class);
        return bookRepository.addBook(book);
    }

    @Override
    public String addBookByIsbn(String isbn) {
       return googleApiWebClient.getWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("q","isbn:" + isbn).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String addBookByTitle(String title) {
        return googleApiWebClient.getWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("q","intitle:" + title).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String addBookByAuthor(String author) {
        return googleApiWebClient.getWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("q","inauthor:" + author).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
