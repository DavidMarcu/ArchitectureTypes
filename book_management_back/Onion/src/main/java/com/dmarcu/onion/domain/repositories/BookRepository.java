package com.dmarcu.onion.domain.repositories;

import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.domain.BookUserCongregate;
import com.dmarcu.onion.domain.Page;

import java.util.List;

public interface BookRepository {

    List<Book> getAll(Page pagination);
    List<Book> getAllBySearchTerm(Page pagination, String searchTerm);
    List<Book> getAllByUserId(int userId, Page pagination);
    List<Book> getAllByUserIdAndSearchTerm(int userId, Page pagination, String searchTerm);
    int getCount();
    int getCountBySearchTerm(String searchTerm);
    int getCountOfUser(int userId);
    int getCountOfUserBySearchTerm(int userId, String searchTerm);
    int getCountOfUserByIsbn(BookUserCongregate bookUserCongregate);
    Book getByIsbn(String isbn);
    String add(Book book);
    void addBookToUser(BookUserCongregate bookUserCongregate);
    void deleteByUsedId(BookUserCongregate bookUserCongregate);
}