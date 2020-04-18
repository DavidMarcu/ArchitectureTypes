package com.dmarcu.layered.presentaion;

import com.dmarcu.layered.application.read.BookDisplay;
import com.dmarcu.layered.application.read.BookReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookReadService bookQueries;

    public BookController(BookReadService bookReadService){
        bookQueries = bookReadService;
    }

    @GetMapping
    public ResponseEntity<List<BookDisplay>> getAllBooks() {
        return ResponseEntity.ok(bookQueries.fetchAll());
    }
}
