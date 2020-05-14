package com.dmarcu.layered.application.commands.book;

import com.dmarcu.layered.application.commands.Command;
import lombok.Data;

@Data
public class AddBookToUserCommand implements Command<AddBookToUserResult> {
    private String username;
    private String isbn;
}
