package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.domain.BookReadDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BooksResult {
    private int nextPage;
    private int prevPage;
    private int lastPage;
    private int totalBooks;
    private int booksPerPage;
    private List<BookReadDto> books;
}
