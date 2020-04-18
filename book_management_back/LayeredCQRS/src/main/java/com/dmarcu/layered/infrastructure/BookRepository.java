package com.dmarcu.layered.infrastructure;

import com.dmarcu.layered.domain.BookReadDto;

import java.util.List;

public interface BookRepository {

    List<BookReadDto> getAll();
}
