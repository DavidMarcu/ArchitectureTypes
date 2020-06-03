package com.dmarcu.onion.outerlayer.controllers;

import com.dmarcu.onion.application.BookService;
import com.dmarcu.onion.application.UserService;
import com.dmarcu.onion.application.exceptions.BookNotFoundException;
import com.dmarcu.onion.application.exceptions.DuplicateBookException;
import com.dmarcu.onion.application.exceptions.OwnershipException;
import com.dmarcu.onion.application.exceptions.PageException;
import com.dmarcu.onion.domain.BookRead;
import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.outerlayer.dtos.*;
import com.dmarcu.onion.outerlayer.mappers.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, UserService userService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.userService = userService;
        this.bookMapper = bookMapper;
    }

    @RequestMapping(value = "/user/book/{isbn}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getBookOwnership(Authentication authentication,
                                                 @PathVariable String isbn) {
        User user = userService.findUserByUsername(authentication.getName());
        bookService.isBookOwned(bookMapper.requestToDomain(user, isbn));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BooksResponse> getAllBooks(@RequestParam(value = "page") int page,
                                                     @RequestParam(value = "q", required = false) String q) {
        int bookCount = bookService.getBookCount(q);
        List<BookRead> books = bookCount > 0 ? bookService.findAllBooks(page, q) : Collections.emptyList();
        return new ResponseEntity<>(bookMapper.domainToResponse(books, bookCount), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<BooksResponse> getAllBooksForUser(Authentication authentication,
                                          @RequestParam(value = "page") int page,
                                          @RequestParam(value = "q", required = false) String q) {
        User user = userService.findUserByUsername(authentication.getName());
        int bookCount = bookService.getBookCountForUser(q, user);
        List<BookRead> books = bookCount > 0 ? bookService.findAllBooksForUser(page, q, user)
                                         : Collections.emptyList();
        return new ResponseEntity<>(bookMapper.domainToResponse(books, bookCount), HttpStatus.OK);
    }

    @GetMapping(value = "book/{isbn}")
    public ResponseEntity<BookResponse> getBookWithIsbn(@PathVariable String isbn) {
        BookRead book = bookService.findBookByIsbn(isbn);
        return new ResponseEntity<>(bookMapper.domainToResponse(book), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponse> insertBook(@Valid @RequestBody BookRequest bookRequest) {
        String bookIsbn = bookService.insertBook(bookMapper.requestToDomain(bookRequest));
        var bookResponse = new BookResponse();
        bookResponse.setIsbn(bookIsbn);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/book")
    public ResponseEntity<ISBNDto> addBookToUser(Authentication authentication,
                                                 @RequestBody ISBNDto isbnDto) {
        User user = userService.findUserByUsername(authentication.getName());
        String bookIdAdded = bookService.addBookToUser(bookMapper.requestToDomain(user, isbnDto));
        return new ResponseEntity<>(bookMapper.domainToResponse(bookIdAdded), HttpStatus.OK);
    }

    @DeleteMapping(value = "/book/{isbn}")
    public ResponseEntity<Void> deleteBookForUser(Authentication authentication,
                                                  @PathVariable String isbn) {
        User user = userService.findUserByUsername(authentication.getName());
        bookService.deleteBookForUser(bookMapper.requestToDomain(user, isbn));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @ExceptionHandler(PageException.class)
    public ResponseEntity<ErrorResponse> handlePageException(PageException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateBookException(DuplicateBookException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<Void> handleOwnershipException() {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
