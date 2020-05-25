package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.exceptions.OwnershipException;
import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.BookUserCongregate;
import com.dmarcu.layered.domain.repositories.BookRepository;
import com.dmarcu.layered.domain.User;
import com.dmarcu.layered.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BookOwnershipHandler implements QueryHandler<Void, BookOwnershipQuery> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookOwnershipHandler(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Void handle(BookOwnershipQuery query) {
        User user = userRepository.getByUsername(query.getUsername());
        int count = bookRepository.getCountOfUserByIsbn(new BookUserCongregate(user.getId(),
                                                        query.getIsbn()));
        if(count < 1) {
            throw new OwnershipException("Book is not owned");
        }
        return null;
    }
}
