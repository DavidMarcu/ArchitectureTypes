package com.dmarcu.layered.application.commands.book;

import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.repositories.BookRepository;
import com.dmarcu.layered.domain.BookUserCongregate;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteBookOfUserHandler implements CommandHandler<Void, DeleteBookOfUserCommand> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public DeleteBookOfUserHandler(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Void handle(DeleteBookOfUserCommand command) {
        User user = userRepository.getByUsername(command.getUsername());
        bookRepository.deleteByUsedId(new BookUserCongregate(user.getId(), command.getIsbn()));
        return null;
    }
}
