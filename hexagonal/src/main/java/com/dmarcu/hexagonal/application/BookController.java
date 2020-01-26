package com.dmarcu.hexagonal.application;

import com.dmarcu.hexagonal.domain.Book;
import com.dmarcu.hexagonal.domain.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    private BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "{isbn}")
    public void getBooks(@PathVariable String isbn){

    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody String book) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book bookObject = objectMapper.readValue(book, Book.class);
        bookRepository.addBook(bookObject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
