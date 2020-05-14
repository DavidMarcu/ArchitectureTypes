package com.dmarcu.layered.domain;

import java.util.List;

public interface BookRepository {

    List<BookReadDto> getAll();
    List<BookReadDto> getAllByUserId(int userId);
    BookReadDto getByIsbn(String isbn);
    void deleteByUsedId(BookUserCongregate bookUserCongregate);
    void addBookToUser(BookUserCongregate bookUserCongregate);
    void add(Book book);
}
