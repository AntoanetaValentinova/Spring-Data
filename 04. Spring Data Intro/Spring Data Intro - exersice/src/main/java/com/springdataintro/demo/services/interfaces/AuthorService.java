package com.springdataintro.demo.services.interfaces;

import com.springdataintro.demo.models.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<Author> getAllAuthorsByTheirBooksCount();
}
