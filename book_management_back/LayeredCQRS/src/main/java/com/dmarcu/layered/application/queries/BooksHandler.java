package com.dmarcu.layered.application.queries;

import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksHandler implements QueryHandler<List<BooksResult>, BooksQuery> {

    private final BookRepository bookRepository;

    public BooksHandler(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BooksResult> handle(BooksQuery query) {
        var books = bookRepository.getAll();
        return books.stream().map(ObjectMappers::convert).collect(Collectors.toList());
    }

}
