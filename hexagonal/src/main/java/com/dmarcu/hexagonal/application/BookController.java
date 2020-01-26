package com.dmarcu.hexagonal.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @GetMapping(value = "{isbn}")
    public void getBooks(@PathVariable String isbn){

    }
}
