package com.dmarcu.layered.application.queries;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.domain.BookReadDto;
import com.dmarcu.layered.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BookHandler implements QueryHandler<BookResult, BookQuery> {

    private final BookRepository bookRepository;
    private final ImageHelper imageHelper;

    public BookHandler(BookRepository bookRepository, ImageHelper imageHelper) {
        this.bookRepository = bookRepository;
        this.imageHelper = imageHelper;
    }

    @Override
    public BookResult handle(BookQuery query) {
        BookReadDto book = bookRepository.getByIsbn(query.getIsbn());
        return convert(book);
    }

    private BookResult convert(BookReadDto bookReadDto) {
        var bookConverted = new BookResult();
        bookConverted.setIsbn(bookReadDto.getIsbn());
        bookConverted.setTitle(bookReadDto.getTitle());
        bookConverted.setDescription(bookReadDto.getDescription());
        bookConverted.setAuthors(Arrays.asList(bookReadDto.getAuthors().split(", ")));
        String[] splittedFilename = bookReadDto.getCoverImage().split("\\.");
        bookConverted.setCoverImageType(splittedFilename[splittedFilename.length - 1]);
        bookConverted.setCoverImage(imageHelper.getImageFromPath(bookReadDto.getCoverImage()));
        return bookConverted;
    }
}
