package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType,int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice,BigDecimal higherPrice);

    List<Book> findAllByReleaseDateLessThanOrReleaseDateGreaterThan(LocalDate releaseDate, LocalDate releaseDate2);

    List<Book> findAllByTitleLike(String pattern);

    List<Book> findAllByAuthor_LastNameStartingWith(String authorLastNamePattern);

    @Query("SELECT COUNT(b.id) FROM Book b WHERE length(b.title)>:minLength")
    int countAllByTitleMinLength(int minLength);

    Book findBookByTitle(String title);

    @Query("UPDATE Book b SET b.copies=b.copies+:copies WHERE b.releaseDate>:date")
    @Modifying
    int updateAllBookCopiesReleasedAfterDate(@Param(value="date")LocalDate releaseDate,
                                             @Param(value="copies")int copies);
    @Modifying
    int deleteBookByCopiesLessThan(Integer copies);


}
