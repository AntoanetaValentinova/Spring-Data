package com.springdataintro.demo.services;

import com.springdataintro.demo.models.entities.Category;
import com.springdataintro.demo.repositories.CategoryRepository;
import com.springdataintro.demo.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_FILE_PATH="src\\main\\resources\\files\\categories.txt";

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if(this.categoryRepository.count()>0) {
            return;
        }

        Files.readAllLines(Path.of(CATEGORY_FILE_PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(categoryName->this.categoryRepository.save(new Category(categoryName)));
    }

    @Override
    public Set<Category> getRandomCategories() {
        long numberOfCategories= ThreadLocalRandom.current().nextLong(1,3);

        Set<Category> randomCategories=new HashSet<>();

        for (int i = 0; i <numberOfCategories ; i++) {
            long randomIdOfCategory=ThreadLocalRandom.current().nextLong(1,this.categoryRepository.count()+1);
            randomCategories.add(this.categoryRepository.findById(randomIdOfCategory).orElse(null));
        }
        return randomCategories;
    }
}
