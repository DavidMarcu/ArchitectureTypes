package com.dmarcu.layered.application.queries.books;

import lombok.Getter;

public abstract class AbstractBooksQuery {

    @Getter
    private final int page;

    @Getter
    private final String searchTerm;

    protected AbstractBooksQuery(int page, String searchTerm) {
        this.page = page;
        this.searchTerm = searchTerm;
    }
}
