package com.dmarcu.layered.application.queries;

import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBooksHandler implements QueryHandler<List<BooksResult>, UserBooksQuery> {

    private final BookRepository bookRepository;
    private final ObjectMappers objectMappers;

    public UserBooksHandler(BookRepository bookRepository, ObjectMappers objectMappers) {
        this.bookRepository = bookRepository;
        this.objectMappers = objectMappers;
    }

    @Override
    public List<BooksResult> handle(UserBooksQuery query) {
        List<BookReadDto> books = bookRepository.getAllByUserId(query.getUserId());
        return books.stream().map(objectMappers::convert).collect(Collectors.toList());
    }
}
