package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findAllByAgeRestriction(ageRestriction).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllByEditionTypeGoldAndCopiesLessThan5000(EditionType editionType, int copies) {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(editionType,copies).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllByPriceLessThan5AndPriceGreaterThan40(BigDecimal lowerPrice, BigDecimal higherPrice) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lowerPrice,higherPrice)
                .stream()
                .map(book -> String.format("%s -$%.2f",book.getTitle(),book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllByReleaseDateLessThanOrReleaseDateGreaterThan(int releaseYear) {
        LocalDate lower=LocalDate.of(releaseYear,1,1);
        LocalDate upper=LocalDate.of(releaseYear,12,31);
        return this.bookRepository.findAllByReleaseDateLessThanOrReleaseDateGreaterThan(lower,upper)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllByReleaseDateLessThan(LocalDate releaseDate) {
        return this.bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(book->String.format("%s %s %.2f",book.getTitle(),book.getEditionType(),book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public int getCountAllByTitleMinLength(int minLength) {
        return this.bookRepository.countAllByTitleMinLength(minLength);
    }


    @Override
    public List<String> getAllByTitleContains(String str) {
        String pattern="%"+str+"%";
        return this.bookRepository.findAllByTitleLike(pattern)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllByAuthorLastNameStartingWith(String authorLastNamePattern) {
        return this.bookRepository.findAllByAuthor_LastNameStartingWith(authorLastNamePattern)
                .stream()
                .map(book -> String.format("%s (%s %s)",book.getTitle(),book.getAuthor().getFirstName(),book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getBookByTitle(String title) {
        Book bookByTitle = this.bookRepository.findBookByTitle(title);
        return String.format("%s %s %s %.2f",bookByTitle.getTitle(),bookByTitle.getEditionType(),bookByTitle.getAgeRestriction(),bookByTitle.getPrice());
    }

    @Override
    @Transactional
    public int updateAllBookCopiesRealeaseAfterDate(LocalDate releaseDate, int copies) {
        return this.bookRepository.updateAllBookCopiesReleasedAfterDate(releaseDate,copies);
    }

    @Override
    @Transactional
    public int deleteBookByCopiesLessThan(Integer copies) {
        return this.bookRepository.deleteBookByCopiesLessThan(copies);
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
