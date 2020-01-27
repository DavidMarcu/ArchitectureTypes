package com.dmarcu.hexagonal.application;

import com.dmarcu.hexagonal.domain.ports.BookServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    private BookServicePort bookService;

    @Autowired
    public BookController(BookServicePort bookServicePort){
        bookService = bookServicePort;
    }

    @GetMapping(value = "{isbn}")
    public void getBooks(@PathVariable String isbn){

    }

    @PostMapping
    public ResponseEntity<AddBookResponse> addBook(@Valid @RequestBody AddBookRequest bookRequest) throws JsonProcessingException {
        String bookIsbnAdded = bookService.addBook(bookRequest);
        return new ResponseEntity<>(new AddBookResponse(bookIsbnAdded), HttpStatus.CREATED);
    }
}
