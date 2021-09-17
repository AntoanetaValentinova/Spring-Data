package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Scanner scan;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(Scanner scan, CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.scan = scan;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
//      printOutputs();

        System.out.println("Please, enter exercise number:");
        String input=scan.nextLine();
        while(input!="END") {
            try {
                int num= Integer.parseInt(input);
                switch (num) {
                    case 1: exerciseOne(); break;
                    case 2: exerciseTwo(); break;
                    case 3: exerciseThree(); break;
                    case 4: exerciseFour(); break;
                    case 5: exerciseFive(); break;
                    case 6: exerciseSix(); break;
                    case 7: exerciseSeven();break;
                    case 8: exerciseEight(); break;
                    case 9: exerciseNine(); break;
                    case 10: exerciseTen(); break;
                    case 11: exerciseEleven(); break;
                    case 12: exerciseTwelve(); break;
                    case 13: exerciseThirteen(); break;
                    case 14: exerciseFourteen(); break;
                }
            } catch (Exception e) {
                System.out.println("Please, try again!");
            } finally {
                System.out.println();
                System.out.println("Please, enter exercise number:");
                input=scan.nextLine();
            }
        }

    }

    private void printOutputs() {
        printAllBooksAfterYear(2000);
        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        printAllAuthorsAndNumberOfTheirBooks();
        pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }

    private void exerciseOne() {
        System.out.println("Please enter Age Restriction (MINOR, TEEN, ADULT):");
        AgeRestriction ageRestriction= AgeRestriction.valueOf(scan.nextLine().toUpperCase());
        this.bookService.getAllBooksByAgeRestriction(ageRestriction)
                .forEach(book-> System.out.println(book));
    }

    private void exerciseTwo() {
        this.bookService.getAllByEditionTypeGoldAndCopiesLessThan5000(EditionType.GOLD,5000)
                .forEach(System.out::println);
    }

    private void exerciseThree() {
        this.bookService.getAllByPriceLessThan5AndPriceGreaterThan40(new BigDecimal(5),new BigDecimal(40))
                .forEach(System.out::println);
    }

    private void exerciseFour() {
        System.out.println("Please, enter year:");
        int year= Integer.parseInt(scan.nextLine());
        this.bookService.getAllByReleaseDateLessThanOrReleaseDateGreaterThan(year)
                .forEach(System.out::println);
    }

    private void exerciseFive() {
        System.out.println("Please, enter date: ");
        LocalDate localDate=LocalDate.parse(scan.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.bookService.getAllByReleaseDateLessThan(localDate)
                .forEach(System.out::println);
    }

    private void exerciseSix() {
        System.out.println("Please enter end pattern:");
        String endString=scan.nextLine();
        this.authorService.getAllByFirstNameEndsWith(endString)
                .forEach(System.out::println);
    }

    private void exerciseSeven() {
        System.out.println("Please ending string pattern:");
        String pattern=scan.nextLine();
        this.bookService.getAllByTitleContains(pattern)
                .forEach(System.out::println);
    }

    private void exerciseEight() {
        System.out.println("Please enter author starts with patter:");
        String str=scan.nextLine();
        this.bookService.getAllByAuthorLastNameStartingWith(str)
                .forEach(System.out::println);
    }

    private void exerciseNine() {
        System.out.println("Please enter min title length:");
        int minLength= Integer.parseInt(scan.nextLine());
        System.out.println(this.bookService.getCountAllByTitleMinLength(minLength));
    }

    private void exerciseTen() {
        List<Map.Entry<String, Integer>> allAuthorsAndTheirBookCopies = this.authorService.getAllAuthorsAndTheirBookCopies();
        allAuthorsAndTheirBookCopies.forEach(kvp->{
            System.out.println(kvp.getKey() +" "+kvp.getValue());
        });

    }

    private void exerciseEleven() {
        System.out.println("Enter book title:");
        String bookTitle=scan.nextLine();
        System.out.println(this.bookService.getBookByTitle(bookTitle));
    }

    private void exerciseTwelve() {
        System.out.println("Please enter release date:");
        String dateInput=scan.nextLine();
        LocalDate date=LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));
        System.out.println("Please enter number of copies for increasing:");
        int copies= Integer.parseInt(scan.nextLine());
        int numOfBooks = this.bookService.updateAllBookCopiesRealeaseAfterDate(date, copies);
        System.out.println(numOfBooks*copies);
    }

    private void exerciseThirteen() {
        System.out.println("Please, enter number of min copies:");
        int copies= Integer.parseInt(scan.nextLine());
        System.out.println(this.bookService.deleteBookByCopiesLessThan(copies)+" books was deleted.");
    }

    private void exerciseFourteen() {
        System.out.println("Please enter author`s first and last name, separated by a single space:");
        String[] fullName=scan.nextLine().split("\\s+");
        int numberOfBooks=this.authorService.getCountOfBooksWrittenByAuthor(fullName[0],fullName[1]);
        System.out.printf("%s %s has written %d books%n",fullName[0],fullName[1],numberOfBooks);
    }


}
