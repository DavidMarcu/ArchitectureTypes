package com.dmarcu.layered.domain.repositories;

import com.dmarcu.layered.domain.BookUserCongregate;
import com.dmarcu.layered.domain.Page;
import com.dmarcu.layered.domain.Review;
import com.dmarcu.layered.domain.ReviewUser;

import java.util.List;

public interface ReviewRepository {

    void add(Review review, BookUserCongregate congregate);
    void update(Review review, BookUserCongregate congregate);
    void delete(BookUserCongregate congregate);
    List<ReviewUser> getForBook(BookUserCongregate congregate, Page pagination);
    ReviewUser getOwnForBook(BookUserCongregate congregate);
    List<Integer> ratingsForBook(String isbn);
}
