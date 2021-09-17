package com.example.springintro.service;

import com.example.springintro.model.entity.Author;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> getAllByFirstNameEndsWith(String str);

    List<Map.Entry<String, Integer>> getAllAuthorsAndTheirBookCopies();

    int getCountOfBooksWrittenByAuthor(String firstName,String lastName);


}
