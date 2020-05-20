package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.exceptions.PageException;
import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBooksHandler extends AbstractBooksHandler implements QueryHandler<BooksResult, UserBooksQuery> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public UserBooksHandler(BookRepository bookRepository, ImageHelper imageHelper,
                            UserRepository userRepository) {
        super(imageHelper);
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BooksResult handle(UserBooksQuery query) {
        if(query.getPage() < 1) {
            throw new PageException("Page must be at least 1");
        }
        User user = userRepository.getByUsername(query.getUsername());
        List<Book> books = bookRepository.getAllByUserId(user.getId(), query.getPage(), pageSize);
        List<BookReadDto> convertedBooks = books.stream().map(this::convert).collect(Collectors.toList());
        int totalBooks = bookRepository.getCountOfUser(user.getId());
        return getBooksResult(query, convertedBooks, totalBooks);
    }


}
