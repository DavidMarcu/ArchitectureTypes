package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.queries.Query;
import lombok.Data;

@Data
public class BookOwnershipQuery implements Query<Void> {
    private final String isbn;
    private final String username;
}
