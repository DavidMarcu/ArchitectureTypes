package com.dmarcu.layered.application;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Invalid credentials");
    }
}
