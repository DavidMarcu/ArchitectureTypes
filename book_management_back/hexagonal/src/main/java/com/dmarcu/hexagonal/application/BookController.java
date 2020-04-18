package com.dmarcu.hexagonal.application;

import com.dmarcu.hexagonal.domain.ports.BookServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private BookServicePort bookService;

    @Autowired
    public BookController(BookServicePort bookServicePort){
        bookService = bookServicePort;
    }

    @GetMapping(value = "isbn/{isbn}")
    public String getBookByIsbn(@PathVariable String isbn){
        return bookService.addBookByIsbn(isbn);
    }

    @GetMapping(value = "title/{title}")
    public String getBookByTitle(@PathVariable String title){
        return bookService.addBookByTitle(title);
    }

    @GetMapping(value = "author/{author}")
    public String getBookByAuthor(@PathVariable String author){
        return bookService.addBookByAuthor(author);
    }

    @PostMapping
    public ResponseEntity<AddBookResponse> addBook(@Valid @RequestBody AddBookRequest bookRequest) throws JsonProcessingException {
        String bookIsbnAdded = bookService.addBook(bookRequest);
        return new ResponseEntity<>(new AddBookResponse(bookIsbnAdded), HttpStatus.CREATED);
    }
}
