package com.dmarcu.layered.domain.repositories;

import com.dmarcu.layered.domain.User;

public interface UserRepository {

    void add(User user);
    User getByUsername(String username);
}
