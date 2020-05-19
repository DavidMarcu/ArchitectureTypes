package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.queries.Query;
import lombok.Data;

@Data
public class BookQuery implements Query<BookResult> {
    private final String isbn;
}
