package com.springdataintro.demo.services;

import com.springdataintro.demo.models.entities.Author;
import com.springdataintro.demo.models.entities.Book;
import com.springdataintro.demo.models.entities.Category;
import com.springdataintro.demo.models.enums.AgeRestriction;
import com.springdataintro.demo.models.enums.EditionType;
import com.springdataintro.demo.repositories.BookRepository;
import com.springdataintro.demo.services.interfaces.AuthorService;
import com.springdataintro.demo.services.interfaces.BookService;
import com.springdataintro.demo.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

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

    private static final String BOOKS_FILE_PATH="src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, CategoryService categoryService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.categoryService=categoryService;
        this.authorService = authorService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count()>0) {
            return;
        }

        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .stream()
                .forEach(row->{
                 String[] bookInfo=row.split("\\s+");
                 Book book=createBook(bookInfo);
                 this.bookRepository.save(book);
                });

    }

    @Override
    public List<Book> getAllBooksAfterYear(int year) {
        return this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(year,12,31));
    }

    @Override
    public List<String> getAllAuthorsWithAtLeastOneBookBeforeYear(int year) {
        return this.bookRepository.findAllByReleaseDateBefore(LocalDate.of(year,1,1))
                .stream()
                .map(book -> String.format("%s %s%n",book.getAuthor().getFirstName(),book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooksByAuthor(String authorFirstName, String authorLastName) {
        return this.bookRepository.findBooksByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(authorFirstName,authorLastName);
    }

    private Book createBook(String[] bookInfo) {
        EditionType editionType=EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate= LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies= Integer.valueOf(bookInfo[2]);
        BigDecimal price=new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction=AgeRestriction.values()[Integer.parseInt(bookInfo[4])];
        String title= Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Set<Category> categories=this.categoryService.getRandomCategories();
        Author author=this.authorService.getRandomAuthor();
        return new Book(title,editionType,price,copies,releaseDate,ageRestriction,author,categories);
    }

}
