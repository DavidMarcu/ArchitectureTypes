package com.dmarcu.layered.presentaion;

import com.dmarcu.layered.application.Bus;
import com.dmarcu.layered.application.commands.book.*;
import com.dmarcu.layered.application.exceptions.BookNotFoundException;
import com.dmarcu.layered.application.exceptions.OwnershipException;
import com.dmarcu.layered.application.exceptions.PageException;
import com.dmarcu.layered.application.queries.books.*;
import com.dmarcu.layered.domain.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Bus applicationBus;

    public BookController(Bus applicationBus){
        this.applicationBus = applicationBus;
    }

    @GetMapping(params = {"page"})
    public ResponseEntity<BooksResult> getAllBooks(@RequestParam int page) {
        return new ResponseEntity<>(applicationBus.executeQuery(new BooksQuery(page)), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/book/{isbn}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getBookOwnership(Authentication authentication,
                                                 @PathVariable String isbn) {
        return new ResponseEntity<>(applicationBus.executeQuery(
                new BookOwnershipQuery(isbn, authentication.getName())), HttpStatus.OK);
    }

    @GetMapping(value = "book/{isbn}")
    public ResponseEntity<BookResult> getBookWithIsbn(@PathVariable String isbn) {
        return new ResponseEntity<>(applicationBus.executeQuery(new BookQuery(isbn)), HttpStatus.OK);
    }

    @GetMapping(value = "/user", params = {"page"})
    public ResponseEntity<BooksResult> getAllBooksForUser(Authentication authentication,
                                                          @RequestParam int page) {
        return new ResponseEntity<>(applicationBus.executeQuery(
                new UserBooksQuery(page, authentication.getName())), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateBookResult> insertBook(@Valid @RequestBody CreateBookCommand bookCommand) {
        return new ResponseEntity<>(applicationBus.executeCommand(bookCommand), HttpStatus.CREATED);
    }

    @PostMapping(value = "/book")
    public ResponseEntity<AddBookToUserResult> addBookToUser(Authentication authentication,
                                                 @RequestBody AddBookToUserCommand bookCommand) {
        bookCommand.setUsername(authentication.getName());
        return new ResponseEntity<>(applicationBus.executeCommand(bookCommand), HttpStatus.OK);
    }

    @DeleteMapping(value = "/book/{isbn}")
    public ResponseEntity<Void>
    deleteBookForUser(Authentication authentication, @PathVariable String isbn) {
        DeleteBookOfUserCommand deleteCommand = new DeleteBookOfUserCommand(authentication.getName(), isbn);
        return new ResponseEntity<>(applicationBus.executeCommand(deleteCommand), HttpStatus.OK);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Error> handleBookNotFoundException(BookNotFoundException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PageException.class)
    public ResponseEntity<Error> handlePageException(PageException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<Void> handleOwnershipException() {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
