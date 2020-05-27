package com.dmarcu.onion.outerlayer.services;

import com.dmarcu.onion.application.UserService;
import com.dmarcu.onion.application.exceptions.DuplicateUserException;
import com.dmarcu.onion.application.exceptions.UserNotFoundException;
import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.domain.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void addUser(User user) {
        if(userRepository.count(user) > 0) {
            throw new DuplicateUserException("User already exists");
        }
        userRepository.add(user);
    }

    @Override
    public User findUser(User user) {
        User domainUser = userRepository.getByUsername(user.getUsername());
        if(domainUser == null || !passwordEncoder.matches(user.getPassword(), domainUser.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }
        return user;
    }
}
