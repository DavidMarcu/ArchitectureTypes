package com.dmarcu.layered.application.queries.books;

import lombok.Getter;

public abstract class AbstractBooksQuery {
    @Getter
    protected final int page;

    public AbstractBooksQuery(int page) {
        this.page = page;
    }
}
