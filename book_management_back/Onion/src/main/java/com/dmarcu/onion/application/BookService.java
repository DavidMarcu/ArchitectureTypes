package com.dmarcu.onion.application;

import com.dmarcu.onion.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks(int page, String searchTerm);
    int getBookCount(String searchTerm);
}
