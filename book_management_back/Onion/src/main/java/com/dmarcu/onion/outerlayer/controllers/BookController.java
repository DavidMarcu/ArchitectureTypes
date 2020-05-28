package com.dmarcu.onion.outerlayer.controllers;

import com.dmarcu.onion.application.BookService;
import com.dmarcu.onion.application.UserService;
import com.dmarcu.onion.application.exceptions.PageException;
import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.domain.BookRead;
import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.outerlayer.dtos.BooksResponse;
import com.dmarcu.onion.outerlayer.dtos.ErrorResponse;
import com.dmarcu.onion.outerlayer.mappers.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(PageException.class)
    public ResponseEntity<ErrorResponse> handlePageException(PageException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
