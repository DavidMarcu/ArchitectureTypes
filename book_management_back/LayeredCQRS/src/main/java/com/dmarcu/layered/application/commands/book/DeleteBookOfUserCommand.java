package com.dmarcu.layered.application.commands.book;

import com.dmarcu.layered.application.commands.Command;
import lombok.Data;

@Data
public class DeleteBookOfUserCommand implements Command<Void> {
    private final String username;
    private final String isbn;
}
