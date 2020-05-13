package com.dmarcu.layered.domain;

import java.util.List;

public interface BookRepository {

    List<BookReadDto> getAll();
    List<BookReadDto> getAllByUserId(int userId);
    BookReadDto getByIsbn(String isbn);
    void add(Book book);
}
