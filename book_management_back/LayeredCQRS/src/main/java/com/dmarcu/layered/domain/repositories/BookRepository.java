package com.dmarcu.layered.domain.repositories;

import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookUserCongregate;
import com.dmarcu.layered.domain.Page;

import java.util.List;

public interface BookRepository {

    List<Book> getAll(Page pagination);
    List<Book> getAllByUserId(int userId, Page pagination);
    List<Book> getAllBySearchTerm(Page pagination, String searchTerm);
    List<Book> getAllByUserIdAndSearchTerm(int userId, Page pagination, String searchTerm);
    int getCountOfUserByIsbn(BookUserCongregate bookUserCongregate);
    Book getByIsbn(String isbn);
    int getCount();
    int getCountOfUser(int userId);
    int getCountBySearchTerm(String searchTerm);
    int getCountOfUserBySearchTerm(int userId, String searchTerm);
    void deleteByUsedId(BookUserCongregate bookUserCongregate);
    void addBookToUser(BookUserCongregate bookUserCongregate);
    void add(Book book);
}
