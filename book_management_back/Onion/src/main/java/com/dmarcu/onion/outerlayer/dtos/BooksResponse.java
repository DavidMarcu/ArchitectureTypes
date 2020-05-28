package com.dmarcu.onion.outerlayer.dtos;

import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.domain.BookRead;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BooksResponse {

    private int lastPage;
    private int totalBooks;
    private int booksPerPage;
    private List<BookRead> books;
}
