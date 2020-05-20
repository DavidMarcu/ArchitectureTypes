package com.dmarcu.layered.application.queries.books;

import com.dmarcu.layered.application.ImageHelper;
import com.dmarcu.layered.application.exceptions.PageException;
import com.dmarcu.layered.domain.Book;
import com.dmarcu.layered.domain.BookReadDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractBooksHandler {

    @Getter
    protected final ImageHelper imageHelper;
    @Value("${books.page_size}")
    protected int pageSize;

    protected AbstractBooksHandler(ImageHelper imageHelper) {
        this.imageHelper = imageHelper;
    }

    protected BooksResult getBooksResult(AbstractBooksQuery query, List<BookReadDto> convertedBooks, int totalBooks) {
        int currentPage = query.getPage();
        int lastPage = getLastPage(totalBooks);
        if(currentPage > lastPage) {
            throw new PageException("Page not available");
        }
        int prevPage = currentPage > 1 ? currentPage - 1 : currentPage;
        int nextPage = currentPage < lastPage ? currentPage + 1 : currentPage;

        return BooksResult.builder()
                .books(convertedBooks)
                .lastPage(lastPage)
                .prevPage(prevPage)
                .nextPage(nextPage)
                .booksPerPage(pageSize)
                .totalBooks(totalBooks)
                .build();
    }

    private int getLastPage(double totalBooks) {
        return (int)Math.ceil(totalBooks / pageSize);
    }

    protected BookReadDto convert(Book book) {
        var bookConverted = new BookReadDto();
        bookConverted.setIsbn(book.getIsbn());
        bookConverted.setTitle(book.getTitle());
        bookConverted.setAuthors(Arrays.asList(book.getAuthors().split(", ")));
        String[] splittedFilename = book.getCoverImage().split("\\.");
        bookConverted.setCoverImageType(splittedFilename[splittedFilename.length - 1]);
        bookConverted.setCoverImage(imageHelper.getImageFromPath(book.getCoverImage()));
        return bookConverted;
    }
}
