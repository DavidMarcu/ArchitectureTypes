package com.dmarcu.layered.application.read;

import com.dmarcu.layered.application.ObjectMappers;
import com.dmarcu.layered.infrastructure.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookReadServiceImpl implements BookReadService {

    private final BookRepository bookRepository;

    public BookReadServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDisplay> fetchAll() {
      var books = bookRepository.getAll();
      return books.stream().map(ObjectMappers::convert).collect(Collectors.toList());
    }
}
