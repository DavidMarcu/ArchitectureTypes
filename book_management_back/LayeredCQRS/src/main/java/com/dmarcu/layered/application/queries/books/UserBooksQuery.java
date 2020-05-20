package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.queries.Query;
import lombok.Getter;

public class UserBooksQuery extends AbstractBooksQuery implements Query<BooksResult> {

    @Getter
    private final String username;

    public UserBooksQuery(int page, String username) {
        super(page);
        this.username = username;
    }
}
