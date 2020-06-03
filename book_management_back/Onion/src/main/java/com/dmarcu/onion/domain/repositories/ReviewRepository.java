package com.dmarcu.onion.domain.repositories;

import com.dmarcu.onion.domain.BookUserCongregate;
import com.dmarcu.onion.domain.Page;
import com.dmarcu.onion.domain.Review;
import com.dmarcu.onion.domain.ReviewUser;

import java.util.List;

public interface ReviewRepository {
    void add(Review review, BookUserCongregate congregate);
    void update(Review review, BookUserCongregate congregate);
    void delete(BookUserCongregate congregate);
    List<ReviewUser> getForBook(BookUserCongregate congregate, Page pagination);
    ReviewUser getOwnForBook(BookUserCongregate congregate);
    List<Integer> ratingsForBook(String isbn);
}
