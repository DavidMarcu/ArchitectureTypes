package com.dmarcu.layered.application.commands.user;

import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddUserHandler implements CommandHandler<AddUserResult, AddUserCommand> {

    private final UserRepository userRepository;
    private final ObjectMappers objectMappers;

    public AddUserHandler(UserRepository userRepository, ObjectMappers objectMappers) {
        this.userRepository = userRepository;
        this.objectMappers = objectMappers;
    }

    @Override
    public AddUserResult handle(AddUserCommand command) {
        User user = objectMappers.convert(command);
        userRepository.add(user);
        return new AddUserResult(user.getUsername());
    }
}
