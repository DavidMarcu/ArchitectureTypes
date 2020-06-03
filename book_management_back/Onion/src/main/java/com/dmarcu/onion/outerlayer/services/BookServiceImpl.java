package com.dmarcu.onion.outerlayer.services;

import com.dmarcu.onion.application.BookService;
import com.dmarcu.onion.application.exceptions.BookNotFoundException;
import com.dmarcu.onion.application.exceptions.DuplicateBookException;
import com.dmarcu.onion.application.exceptions.OwnershipException;
import com.dmarcu.onion.application.exceptions.PageException;
import com.dmarcu.onion.domain.*;
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
    @Value("${books.image_default}")
    private String defaultImagePath;

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

    @Override
    public BookRead findBookByIsbn(String isbn) {
        Book book = bookRepository.getByIsbn(isbn);
        if(book == null) {
            throw new BookNotFoundException("Book not found");
        }
        return convert(book);
    }

    @Override
    public String insertBook(BookRead book) {
       String bookIsbn = bookRepository.add(convert(book));
       if(bookIsbn == null) {
           throw new DuplicateBookException("Book already exists");
       }
       return bookIsbn;
    }

    @Override
    public String addBookToUser(BookUserCongregate bookUserCongregate) {
        bookRepository.addBookToUser(bookUserCongregate);
        return bookUserCongregate.getIsbn();
    }

    @Override
    public void deleteBookForUser(BookUserCongregate bookUserCongregate) {
        bookRepository.deleteByUsedId(bookUserCongregate);
    }

    @Override
    public void isBookOwned(BookUserCongregate bookUserCongregate) {
        int count = bookRepository.getCountOfUserByIsbn(bookUserCongregate);
        if(count < 1) {
            throw new OwnershipException("Book is not owned");
        }
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

    private Book convert(BookRead book) {
        String authors = String.join(", ", book.getAuthors());
        String imagePath = book.getCoverImage() != null
                ? imageHelper.uploadImage(book.getCoverImageType(), book.getCoverImage().getBytes())
                : defaultImagePath;
        return new Book(book.getIsbn(), authors, book.getTitle(), imagePath,
                book.getEditionNumber(), book.getDescription());
    }
}
