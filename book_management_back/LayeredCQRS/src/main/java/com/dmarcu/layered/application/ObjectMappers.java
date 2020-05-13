package com.dmarcu.layered.application;

import com.dmarcu.layered.application.commands.user.AddUserCommand;
import com.dmarcu.layered.application.commands.book.CreateBookCommand;
import com.dmarcu.layered.application.queries.BooksResult;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ObjectMappers {

    private final ImageHelper imageHelper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${books.image_default}")
    private String defaultImagePath;

    public ObjectMappers(ImageHelper imageHelper) {
        this.imageHelper = imageHelper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public BooksResult convert(BookReadDto bookReadDto) {
        var bookConverted = new BooksResult();
        bookConverted.setIsbn(bookReadDto.getIsbn());
        bookConverted.setTitle(bookReadDto.getTitle());
        bookConverted.setAuthors(Arrays.asList(bookReadDto.getAuthors().split(", ")));
        String[] splittedFilename = bookReadDto.getCoverImage().split("\\.");
        bookConverted.setCoverImageType(splittedFilename[splittedFilename.length - 1]);
        bookConverted.setCoverImage(imageHelper.getImageFromPath(bookReadDto.getCoverImage()));
        return bookConverted;
    }

    public Book convert(CreateBookCommand bookCommand) {
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
        book.setCoverImagePath(imagePath);
        return book;
    }

    public User convert(AddUserCommand addUserCommand) {
        var user = new User();
        user.setUsername(addUserCommand.getUsername());
        user.setEmail(addUserCommand.getEmail());
        user.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));
        return user;
    }

}
