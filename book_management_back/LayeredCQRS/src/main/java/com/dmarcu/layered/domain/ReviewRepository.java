package com.dmarcu.layered.domain;

import java.util.List;

public interface ReviewRepository {

    void add(Review review, BookUserCongregate congregate);
    void update(Review review, BookUserCongregate congregate);
    void delete(BookUserCongregate congregate);
    List<ReviewUser> getForBook(String isbn);
}
