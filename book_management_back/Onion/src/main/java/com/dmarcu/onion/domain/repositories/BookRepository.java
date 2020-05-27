package com.dmarcu.onion.domain.repositories;

import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.domain.Page;

import java.util.List;

public interface BookRepository {

    List<Book> getAll(Page pagination);
    List<Book> getAllBySearchTerm(Page pagination, String searchTerm);
    int getCount();
    int getCountBySearchTerm(String searchTerm);
}
