package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.exceptions.PageException;
import com.dmarcu.layered.application.queries.QueryHandler;
import com.dmarcu.layered.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        List<Book> books;
        int totalBooks;
        if(query.getSearchTerm() != null) {
            totalBooks = bookRepository.getCountOfUserBySearchTerm(user.getId(), query.getSearchTerm());
            books = totalBooks > 0
                    ? bookRepository.getAllByUserIdAndSearchTerm(user.getId(), query.getPage(),
                    pageSize, query.getSearchTerm())
                    : Collections.emptyList();
        } else {
            totalBooks = bookRepository.getCountOfUser(user.getId());
            books = totalBooks > 0
                    ? bookRepository.getAllByUserId(user.getId(), query.getPage(), pageSize)
                    : Collections.emptyList();
        }
        List<BookReadDto> convertedBooks = books.stream().map(this::convert).collect(Collectors.toList());
        return getBooksResult(query, convertedBooks, totalBooks);
    }


}
