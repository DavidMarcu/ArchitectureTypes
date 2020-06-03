package com.dmarcu.onion.outerlayer.mappers;

import com.dmarcu.onion.domain.BookRead;
import com.dmarcu.onion.domain.BookUserCongregate;
import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.outerlayer.dtos.BookRequest;
import com.dmarcu.onion.outerlayer.dtos.BookResponse;
import com.dmarcu.onion.outerlayer.dtos.BooksResponse;
import com.dmarcu.onion.outerlayer.dtos.ISBNDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    @Value("${books.page_size}")
    private int bookPageSize;

    public BooksResponse domainToResponse(List<BookRead> books, int bookCount) {
        return new BooksResponse(getLastPage(bookCount), bookCount, bookPageSize, books);
    }

    public BookResponse domainToResponse(BookRead book) {
        return new BookResponse(book.getIsbn(), book.getTitle(), book.getAuthors() ,
                book.getCoverImage(), book.getCoverImageType(), book.getDescription());
    }

    public BookRead requestToDomain(BookRequest bookRequest) {
        return new BookRead(bookRequest.getIsbn(), bookRequest.getTitle(), bookRequest.getAuthors(),
                bookRequest.getCoverImage(), bookRequest.getCoverImageType(),
                bookRequest.getDescription(), bookRequest.getEditionNumber());
    }

    public BookUserCongregate requestToDomain(User user, ISBNDto isbn) {
        return new BookUserCongregate(user.getId(), isbn.getIsbn());
    }

    public BookUserCongregate requestToDomain(User user, String isbn) {
        return new BookUserCongregate(user.getId(), isbn);
    }

    public ISBNDto domainToResponse(String isbn) {
        return new ISBNDto(isbn);
    }

    private int getLastPage(double totalBooks) {
        return (int)Math.ceil(totalBooks / bookPageSize);
    }
}
