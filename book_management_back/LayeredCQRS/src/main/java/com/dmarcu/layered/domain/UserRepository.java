package com.dmarcu.layered.domain;

public interface UserRepository {

    void add(User user);
    User getByUsername(String username);
}
