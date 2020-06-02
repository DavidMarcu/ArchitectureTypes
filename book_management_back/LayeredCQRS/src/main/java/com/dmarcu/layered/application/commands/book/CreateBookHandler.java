package com.dmarcu.layered.application.commands.book;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.application.exceptions.DuplicateBookException;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateBookHandler implements CommandHandler<CreateBookResult, CreateBookCommand> {

    private final BookRepository bookRepository;
    private final ImageHelper imageHelper;

    @Value("${books.image_default}")
    private String defaultImagePath;

    public CreateBookHandler(BookRepository bookRepository, ImageHelper imageHelper) {
        this.bookRepository = bookRepository;
        this.imageHelper = imageHelper;
    }

    @Override
    public CreateBookResult handle(CreateBookCommand command) {
        Book book = convert(command);
        String bookIsbn = bookRepository.add(book);
        if(bookIsbn == null) {
            throw new DuplicateBookException("Book already exists");
        }
        return new CreateBookResult(book.getIsbn());
    }

    private Book convert(CreateBookCommand bookCommand) {
        var book = new Book();
        book.setIsbn(bookCommand.getIsbn());
        book.setTitle(bookCommand.getTitle());
        String authors = String.join(", ", bookCommand.getAuthors());
        book.setAuthors(authors);
        book.setEditionNumber(bookCommand.getEditionNumber());
        book.setYearPublished(bookCommand.getYearPublished());
        book.setDescription(bookCommand.getDescription());
        String imagePath = bookCommand.getCoverImage() != null
                ? imageHelper.uploadImage(bookCommand.getCoverImageType(), bookCommand.getCoverImage())
                : defaultImagePath;
        book.setCoverImage(imagePath);
        return book;
    }
}
