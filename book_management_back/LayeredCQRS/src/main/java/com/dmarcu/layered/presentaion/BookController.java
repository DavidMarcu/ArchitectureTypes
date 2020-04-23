package com.dmarcu.layered.presentaion;

import com.dmarcu.layered.application.Bus;
import com.dmarcu.layered.application.queries.BooksQuery;
import com.dmarcu.layered.application.queries.BooksResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/book")
//    public ResponseEntity<String> insertBook(@RequestBody String) {
//
//    }
}
