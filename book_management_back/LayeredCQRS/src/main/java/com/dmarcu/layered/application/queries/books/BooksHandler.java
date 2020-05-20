package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.exceptions.PageException;
import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

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
        var books = bookRepository.getAll(query.getPage(), pageSize);
        List<BookReadDto> convertedBooks = books.stream().map(this::convert).collect(Collectors.toList());
        int totalBooks = bookRepository.getCount();
        return getBooksResult(query, convertedBooks, totalBooks);
    }


}
