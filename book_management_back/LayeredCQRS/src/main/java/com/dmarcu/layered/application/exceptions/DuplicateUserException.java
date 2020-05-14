package com.dmarcu.layered.application.exceptions;

public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException(String message) {
        super(message);
    }
}
