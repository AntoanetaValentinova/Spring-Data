package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> getAllBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<String> getAllByEditionTypeGoldAndCopiesLessThan5000(EditionType editionType,int copies);

    List<String> getAllByPriceLessThan5AndPriceGreaterThan40(BigDecimal lowerPrice,BigDecimal higherPrice);

    List<String> getAllByReleaseDateLessThanOrReleaseDateGreaterThan(int releaseYear);

    List<String> getAllByReleaseDateLessThan(LocalDate releaseDate);

    int getCountAllByTitleMinLength(int minLength);

    List<String> getAllByTitleContains(String pattern);

    List<String> getAllByAuthorLastNameStartingWith(String authorLastNamePattern);

    String getBookByTitle(String title);

    int updateAllBookCopiesRealeaseAfterDate(LocalDate releaseDate,int copies);

    int deleteBookByCopiesLessThan(Integer copies);


}
