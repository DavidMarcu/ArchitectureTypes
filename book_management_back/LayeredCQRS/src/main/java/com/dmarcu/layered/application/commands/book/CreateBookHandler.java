package com.dmarcu.layered.application.commands.book;

import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBookHandler implements CommandHandler<CreateBookResult, CreateBookCommand> {

    private final BookRepository bookRepository;
    private final ObjectMappers objectMapper;

    public CreateBookHandler(BookRepository bookRepository, ObjectMappers objectMapper) {
        this.bookRepository = bookRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public CreateBookResult handle(CreateBookCommand command) {
        Book book = objectMapper.convert(command);
        bookRepository.add(book);
        return new CreateBookResult(book.getIsbn());
    }
}
