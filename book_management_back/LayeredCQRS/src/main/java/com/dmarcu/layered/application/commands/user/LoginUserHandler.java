package com.dmarcu.layered.application.commands.user;

import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.application.exceptions.UserNotFoundException;
import com.dmarcu.layered.domain.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class LoginUserHandler implements CommandHandler<LoginUserResult, LoginUserCommand> {

    private final UserRepository userRepository;
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    public LoginUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginUserResult handle(LoginUserCommand command) {
        var user = userRepository.getByUsername(command.getUsername());
        var passwordEncoder = new BCryptPasswordEncoder();
        var loginUserResult = new LoginUserResult();
        if(passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            String credsTobeEncoded = command.getUsername() + ":" + command.getPassword();
            String base64EncodedCredentials = Base64.getEncoder()
                    .encodeToString(credsTobeEncoded.getBytes());
            loginUserResult.setAuthorizationHeader(AUTHORIZATION_HEADER_PREFIX + base64EncodedCredentials);
            return loginUserResult;
        } else {
            throw new UserNotFoundException("Invalid credentials");
        }
    }
}
