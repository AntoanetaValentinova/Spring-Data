package com.springdataintro.demo;

import com.springdataintro.demo.models.entities.Author;
import com.springdataintro.demo.models.entities.Book;
import com.springdataintro.demo.services.interfaces.AuthorService;
import com.springdataintro.demo.services.interfaces.BookService;
import com.springdataintro.demo.services.interfaces.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.println("Get all books after the year 2000. Print");
        printAllBooksAfterYear(2000);
        System.out.println("Get all authors with at least one book with release date before 1990.");
        printAllAuthorsWithAtLeastOneBookBeforeYear(1990);
        System.out.println("Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count.");
        printAllAuthorsByTheirBooksCountDesc();
        System.out.println("Get all books from author George Powell, ordered by their release date (descending), then by book title (ascending). Print the book's title, release date and copies.");
        printAllBooksByAuthor("George","Powell");
    }

    private void printAllBooksByAuthor(String firstName, String lastName) {
        this.bookService.getAllBooksByAuthor(firstName,lastName)
                .forEach(book -> System.out.printf("%s %s %d%n",book.getTitle(),book.getReleaseDate(),book.getCopies()));
    }

    private void printAllAuthorsByTheirBooksCountDesc() {
    this.authorService.getAllAuthorsByTheirBooksCount()
                .forEach(author -> System.out.printf("%s %s %d%n",author.getFirstName(),author.getLastName(),author.getBooks().size()));
    }

    private void printAllAuthorsWithAtLeastOneBookBeforeYear(int year) {
        this.bookService.getAllAuthorsWithAtLeastOneBookBeforeYear(year)
                .forEach(author-> System.out.print(author));
    }

    private void printAllBooksAfterYear(int year) {
        this.bookService.getAllBooksAfterYear(year)
                .forEach(book-> System.out.printf("Title: %s,Release date: %s%n",book.getTitle(),book.getReleaseDate()));
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
