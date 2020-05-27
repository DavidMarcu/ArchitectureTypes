package com.dmarcu.onion.outerlayer.services;

import com.dmarcu.onion.application.BookService;
import com.dmarcu.onion.application.exceptions.PageException;
import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.domain.Page;
import com.dmarcu.onion.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Value("${books.page_size}")
    private int bookPageSize;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks(int page, String searchTerm) {
        if(page < 1) {
            throw new PageException("Page must be at least 1");
        }
        return searchTerm == null ? bookRepository.getAll(new Page(page, bookPageSize))
                                  : bookRepository.getAllBySearchTerm(new Page(page, bookPageSize), searchTerm);
    }

    @Override
    public int getBookCount(String searchTerm) {
        return searchTerm == null ? bookRepository.getCount() : bookRepository.getCountBySearchTerm(searchTerm);
    }
}
