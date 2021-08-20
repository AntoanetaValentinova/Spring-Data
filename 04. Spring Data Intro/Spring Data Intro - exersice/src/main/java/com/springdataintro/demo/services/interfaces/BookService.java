package com.springdataintro.demo.services.interfaces;

import com.springdataintro.demo.models.entities.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBooksAfterYear(int year);

    List<String> getAllAuthorsWithAtLeastOneBookBeforeYear(int year);

    List<Book> getAllBooksByAuthor(String authorFirstName,String authorLastName);
}
