package com.dmarcu.layered.application.queries;

import lombok.Data;

@Data
public class BookQuery implements Query<BookResult> {
    private final String isbn;
}
