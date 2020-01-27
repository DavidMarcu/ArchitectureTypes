package com.dmarcu.hexagonal.domain.ports;

import com.dmarcu.hexagonal.application.AddBookRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BookServicePort {

    String addBook(AddBookRequest book) throws JsonProcessingException;
}
