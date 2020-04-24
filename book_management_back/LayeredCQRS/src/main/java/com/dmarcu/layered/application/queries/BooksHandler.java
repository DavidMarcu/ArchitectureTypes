package com.dmarcu.layered.application.queries;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksHandler implements QueryHandler<List<BooksResult>, BooksQuery> {

    private final BookRepository bookRepository;
    private final ObjectMappers objectMapper;

    public BooksHandler(BookRepository bookRepository, ObjectMappers objectMapper){
        this.bookRepository = bookRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<BooksResult> handle(BooksQuery query) {
        var books = bookRepository.getAll();
        return books.stream().map(objectMapper::convert).collect(Collectors.toList());
    }

}
