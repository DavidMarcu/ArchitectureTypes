package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.queries.Query;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class BooksQuery extends AbstractBooksQuery implements Query<BooksResult> {

    public BooksQuery(int page) {
        super(page);
    }
}
