package com.dmarcu.layered.application.commands.book;

import lombok.Data;

@Data
public class AddBookToUserResult {
    private final String isbn;
}
