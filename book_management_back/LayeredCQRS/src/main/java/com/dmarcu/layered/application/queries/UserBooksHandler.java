package com.dmarcu.layered.application.queries;

import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.BookRepository;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBooksHandler implements QueryHandler<List<BooksResult>, UserBooksQuery> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ObjectMappers objectMappers;

    public UserBooksHandler(BookRepository bookRepository, ObjectMappers objectMappers,
                            UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.objectMappers = objectMappers;
        this.userRepository = userRepository;
    }

    @Override
    public List<BooksResult> handle(UserBooksQuery query) {
        User user = userRepository.getByUsername(query.getUsername());
        List<BookReadDto> books = bookRepository.getAllByUserId(user.getId());
        return books.stream().map(objectMappers::convert).collect(Collectors.toList());
    }
}
