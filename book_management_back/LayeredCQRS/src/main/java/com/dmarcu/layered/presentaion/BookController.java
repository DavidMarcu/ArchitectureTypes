package com.dmarcu.layered.presentaion;

import com.dmarcu.layered.application.Bus;
import com.dmarcu.layered.application.commands.book.CreateBookCommand;
import com.dmarcu.layered.application.commands.book.CreateBookResult;
import com.dmarcu.layered.application.exceptions.BookNotFoundException;
import com.dmarcu.layered.application.queries.*;
import com.dmarcu.layered.domain.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Bus applicationBus;

    public BookController(Bus applicationBus){
        this.applicationBus = applicationBus;
    }

    @GetMapping
    public ResponseEntity<List<BooksResult>> getAllBooks() {
        return new ResponseEntity<>(applicationBus.executeQuery(new BooksQuery()), HttpStatus.OK);
    }

    @GetMapping(value = "book/{isbn}")
    public ResponseEntity<BookResult> getBookWithIsbn(@PathVariable String isbn) {
        return new ResponseEntity<>(applicationBus.executeQuery(new BookQuery(isbn)), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<BooksResult>> getAllBooksForUser(@PathVariable int userId) {
        return new ResponseEntity<>(applicationBus.executeQuery(new UserBooksQuery(userId)), HttpStatus.OK);
    }

    @PostMapping(value = "/book")
    public ResponseEntity<CreateBookResult> insertBook(@Valid @RequestBody CreateBookCommand bookCommand) {
        return new ResponseEntity<>(applicationBus.executeCommand(bookCommand), HttpStatus.CREATED);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFoundException(BookNotFoundException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
