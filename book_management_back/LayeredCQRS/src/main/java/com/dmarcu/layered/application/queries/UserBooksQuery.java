package com.dmarcu.layered.application.queries;

import lombok.Data;

import java.util.List;

@Data
public class UserBooksQuery implements Query<List<BooksResult>> {
    private final String username;
}
