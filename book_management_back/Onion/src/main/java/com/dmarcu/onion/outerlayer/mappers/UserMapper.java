package com.dmarcu.onion.outerlayer.mappers;

import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.outerlayer.dtos.UserLoginResponse;
import com.dmarcu.onion.outerlayer.dtos.UserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserMapper() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User requestToDomain(UserRequest userRequest) {
        return new User(userRequest.getUsername(), userRequest.getEmail(),
                passwordEncoder.encode(userRequest.getPassword()));
    }

    public User loginRequestToDomain(UserRequest userRequest) {
        return new User(userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword());
    }

    public UserLoginResponse domainToLoginResponse(User user) {
        String credsTobeEncoded = user.getUsername() + ":" + user.getPassword();
        String base64EncodedCredentials = Base64.getEncoder()
                .encodeToString(credsTobeEncoded.getBytes());
        return new UserLoginResponse(base64EncodedCredentials);
    }
}
