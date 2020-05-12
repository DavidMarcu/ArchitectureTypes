package com.dmarcu.layered.domain;

import java.util.List;

public interface BookRepository {

    List<BookReadDto> getAll();
    List<BookReadDto> getAllByUserId(int userId);
    void add(Book book);
}
