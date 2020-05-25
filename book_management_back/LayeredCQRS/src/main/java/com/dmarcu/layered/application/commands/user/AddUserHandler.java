package com.dmarcu.layered.application.commands.user;

import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AddUserHandler implements CommandHandler<AddUserResult, AddUserCommand> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AddUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public AddUserResult handle(AddUserCommand command) {
        User user = convert(command);
        userRepository.add(user);
        return new AddUserResult(user.getUsername());
    }

    public User convert(AddUserCommand addUserCommand) {
        var user = new User();
        user.setUsername(addUserCommand.getUsername());
        user.setEmail(addUserCommand.getEmail());
        user.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));
        return user;
    }
}
