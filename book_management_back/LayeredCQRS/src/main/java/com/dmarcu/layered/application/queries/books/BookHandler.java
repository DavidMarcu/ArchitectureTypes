package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BookHandler implements QueryHandler<BookResult, BookQuery> {

    private final BookRepository bookRepository;
    private final ImageHelper imageHelper;

    public BookHandler(BookRepository bookRepository, ImageHelper imageHelper) {
        this.bookRepository = bookRepository;
        this.imageHelper = imageHelper;
    }

    @Override
    public BookResult handle(BookQuery query) {
        Book book = bookRepository.getByIsbn(query.getIsbn());
        return convert(book);
    }

    private BookResult convert(Book book) {
        var bookConverted = new BookResult();
        bookConverted.setIsbn(book.getIsbn());
        bookConverted.setTitle(book.getTitle());
        bookConverted.setDescription(book.getDescription());
        bookConverted.setAuthors(Arrays.asList(book.getAuthors().split(", ")));
        String[] splittedFilename = book.getCoverImage().split("\\.");
        bookConverted.setCoverImageType(splittedFilename[splittedFilename.length - 1]);
        bookConverted.setCoverImage(imageHelper.getImageFromPath(book.getCoverImage()));
        return bookConverted;
    }
}
