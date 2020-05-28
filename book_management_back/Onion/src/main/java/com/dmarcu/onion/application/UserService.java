package com.dmarcu.onion.application;

import com.dmarcu.onion.domain.User;

public interface UserService {

    void addUser(User user);
    User findUser(User user);
    User findUserByUsername(String username);
}
