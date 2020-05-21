package com.dmarcu.layered.domain;

import java.util.List;

public interface BookRepository {

    List<Book> getAll(int page, int pageSize);
    List<Book> getAllByUserId(int userId, int page, int pageSize);
    List<Book> getAllBySearchTerm(int page, int pageSize, String searchTerm);
    List<Book> getAllByUserIdAndSearchTerm(int userId, int page, int pageSize, String searchTerm);
    int getCountOfUserByIsbn(int userId, String isbn);
    Book getByIsbn(String isbn);
    int getCount();
    int getCountOfUser(int userId);
    int getCountBySearchTerm(String searchTerm);
    int getCountOfUserBySearchTerm(int userId, String searchTerm);
    void deleteByUsedId(BookUserCongregate bookUserCongregate);
    void addBookToUser(BookUserCongregate bookUserCongregate);
    void add(Book book);
}
