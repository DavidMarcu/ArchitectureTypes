package com.dmarcu.onion.outerlayer.controllers;

import com.dmarcu.onion.application.BookService;
import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.outerlayer.dtos.BooksResponse;
import com.dmarcu.onion.outerlayer.mappers.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<BooksResponse> getAllBooks(@RequestParam(value = "page") int page,
                                                     @RequestParam(value = "q", required = false) String q) {
        int bookCount = bookService.getBookCount(q);
        List<Book> books = bookCount > 0 ? bookService.findAllBooks(page, q) : Collections.emptyList();
        return new ResponseEntity<>(bookMapper.domainToResponse(books, bookCount), HttpStatus.OK);
    }

}
