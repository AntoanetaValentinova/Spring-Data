package com.springdataintro.demo.services;

import com.springdataintro.demo.models.entities.Author;
import com.springdataintro.demo.repositories.AuthorRepository;
import com.springdataintro.demo.services.interfaces.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String AUTHORS_FILE_PATH="src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count()>0) {
            return;
        }

        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
        .stream()
        .forEach(row->{
            String[] authorInfo=row.split("\\s+");
            this.authorRepository.save(new Author(authorInfo[0],authorInfo[1]));
        });
    }

    @Override
    public Author getRandomAuthor() {
        long randomIdOfAuthor = ThreadLocalRandom.current().nextLong(1,this.authorRepository.count());
        return this.authorRepository.findById(randomIdOfAuthor).orElse(null);
    }

    @Override
    public List<Author> getAllAuthorsByTheirBooksCount() {
        return this.authorRepository.findAllByBooksSizeDESC();
    }

}
