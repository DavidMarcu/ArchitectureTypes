package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.exceptions.PageException;
import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksHandler extends AbstractBooksHandler implements QueryHandler<BooksResult, BooksQuery> {

    private final BookRepository bookRepository;

    public BooksHandler(BookRepository bookRepository, ImageHelper imageHelper){
        super(imageHelper);
        this.bookRepository = bookRepository;
    }

    @Override
    public BooksResult handle(BooksQuery query) {
        if(query.getPage() < 1) {
            throw new PageException("Page must be at least 1");
        }
        List<Book> books;
        int totalBooks;
        if(query.getSearchTerm() != null) {
            totalBooks = bookRepository.getCountBySearchTerm(query.getSearchTerm());
            books = totalBooks > 0
                    ? bookRepository.getAllBySearchTerm(query.getPage(), pageSize, query.getSearchTerm())
                    : Collections.emptyList();
        } else {
            totalBooks = bookRepository.getCount();
            books = bookRepository.getAll(query.getPage(), pageSize);
        }
        List<BookReadDto> convertedBooks = books.stream().map(this::convert).collect(Collectors.toList());
        return getBooksResult(query, convertedBooks, totalBooks);
    }


}
