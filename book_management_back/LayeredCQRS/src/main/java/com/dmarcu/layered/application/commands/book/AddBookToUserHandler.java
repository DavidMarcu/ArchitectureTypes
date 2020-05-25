package com.dmarcu.layered.application.commands.book;

import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.repositories.BookRepository;
import com.dmarcu.layered.domain.BookUserCongregate;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddBookToUserHandler implements CommandHandler<AddBookToUserResult, AddBookToUserCommand> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public AddBookToUserHandler(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddBookToUserResult handle(AddBookToUserCommand command) {
        User user = userRepository.getByUsername(command.getUsername());
        bookRepository.addBookToUser(new BookUserCongregate(user.getId(), command.getIsbn()));
        return new AddBookToUserResult(command.getIsbn());
    }
}
