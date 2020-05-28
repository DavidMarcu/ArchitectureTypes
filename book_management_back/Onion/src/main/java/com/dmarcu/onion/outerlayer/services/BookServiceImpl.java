package com.dmarcu.onion.outerlayer.services;

import com.dmarcu.onion.application.BookService;
import com.dmarcu.onion.application.exceptions.PageException;
import com.dmarcu.onion.domain.Book;
import com.dmarcu.onion.domain.BookRead;
import com.dmarcu.onion.domain.Page;
import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.domain.repositories.BookRepository;
import com.dmarcu.onion.outerlayer.helpers.ImageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ImageHelper imageHelper;
    @Value("${books.page_size}")
    private int bookPageSize;

    public BookServiceImpl(BookRepository bookRepository, ImageHelper imageHelper) {
        this.bookRepository = bookRepository;
        this.imageHelper = imageHelper;
    }

    @Override
    public List<BookRead> findAllBooks(int page, String searchTerm) {
        if(page < 1) {
            throw new PageException("Page must be at least 1");
        }
        List<Book> books = searchTerm == null
                ? bookRepository.getAll(new Page(page, bookPageSize))
                : bookRepository.getAllBySearchTerm(new Page(page, bookPageSize), searchTerm);
        if(books.isEmpty()) {
            throw new PageException("Page not available");
        }
        return books.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public List<BookRead> findAllBooksForUser(int page, String searchTerm, User user) {
        if(page < 1) {
            throw new PageException("Page must be at least 1");
        }
        List<Book> books = searchTerm == null
                ? bookRepository.getAllByUserId(user.getId(), new Page(page, bookPageSize))
                : bookRepository.getAllByUserIdAndSearchTerm(user.getId(), new Page(page, bookPageSize),
                                                             searchTerm);
        if(books.isEmpty()) {
            throw new PageException("Page not available");
        }
        return books.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public int getBookCount(String searchTerm) {
        return searchTerm == null ? bookRepository.getCount() : bookRepository.getCountBySearchTerm(searchTerm);
    }

    @Override
    public int getBookCountForUser(String searchTerm, User user) {
        return searchTerm == null ? bookRepository.getCountOfUser(user.getId())
                                  : bookRepository.getCountOfUserBySearchTerm(user.getId(), searchTerm);
    }

    private BookRead convert(Book book) {
        List<String> authors = Arrays.asList(book.getAuthors().split(", "));
        String[] splittedFilename = book.getCoverImage().split("\\.");
        return new BookRead(book.getIsbn(),
                book.getTitle(),
                authors,
                imageHelper.getImageFromPath(book.getCoverImage()),
                splittedFilename[splittedFilename.length - 1],
                book.getDescription());
    }
}
