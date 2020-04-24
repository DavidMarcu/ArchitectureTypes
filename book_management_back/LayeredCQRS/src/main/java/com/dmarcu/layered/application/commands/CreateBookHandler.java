package com.dmarcu.layered.application.commands;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBookHandler implements CommandHandler<CreateBookResult, CreateBookCommand> {

    private final BookRepository bookRepository;
    private final ImageHelper imageHelper;

    public CreateBookHandler(BookRepository bookRepository, ImageHelper imageHelper) {
        this.bookRepository = bookRepository;
        this.imageHelper = imageHelper;
    }

    @Override
    public CreateBookResult handle(CreateBookCommand command) {
        String imagePath = imageHelper.uploadImage(command.getCoverImageType(), command.getCoverImage());
        Book book = ObjectMappers.convert(command);
        book.setCoverImagePath(imagePath);
        bookRepository.add(book);
        return new CreateBookResult(book.getIsbn());
    }
}
