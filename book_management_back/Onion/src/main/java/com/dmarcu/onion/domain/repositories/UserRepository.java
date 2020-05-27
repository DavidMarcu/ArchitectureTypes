package com.dmarcu.onion.domain.repositories;

import com.dmarcu.onion.domain.User;

public interface UserRepository {

    void add(User user);
    int count(User user);
    User getByUsername(String username);
}
