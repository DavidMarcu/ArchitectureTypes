package com.dmarcu.layered.application;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}
