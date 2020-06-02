package com.dmarcu.onion.application;

import com.dmarcu.onion.domain.BookRead;
import com.dmarcu.onion.domain.User;

import java.util.List;

public interface BookService {

    List<BookRead> findAllBooks(int page, String searchTerm);
    List<BookRead> findAllBooksForUser(int page, String searchTerm, User user);
    int getBookCount(String searchTerm);
    int getBookCountForUser(String searchTerm, User user);
    BookRead findBookByIsbn(String isbn);
    String insertBook(BookRead book);
}
