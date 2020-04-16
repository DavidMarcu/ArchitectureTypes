package com.dmarcu.hexagonal.domain.ports;

import com.dmarcu.hexagonal.domain.Book;

public interface BookRepositoryPort {

    String addBook(Book book);
}
