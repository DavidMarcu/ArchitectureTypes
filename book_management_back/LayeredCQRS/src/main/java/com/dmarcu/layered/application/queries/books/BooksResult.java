package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.domain.BookRead;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BooksResult {

    private int lastPage;
    private int totalBooks;
    private int booksPerPage;
    private List<BookRead> books;
}
