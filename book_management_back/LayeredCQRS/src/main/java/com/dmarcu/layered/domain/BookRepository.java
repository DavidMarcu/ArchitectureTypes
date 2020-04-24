package com.dmarcu.layered.domain;

import java.util.List;

public interface BookRepository {

    List<BookReadDto> getAll();
    void add(Book book);
}
